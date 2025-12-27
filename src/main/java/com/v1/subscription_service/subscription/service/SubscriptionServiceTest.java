package com.v1.subscription_service.subscription.service;

import com.v1.subscription_service.subscription.api.SubscriptionDto;
import com.v1.subscription_service.subscription.api.SubscriptionRequest;
import com.v1.subscription_service.subscription.model.Plan;
import com.v1.subscription_service.subscription.model.Subscription;
import com.v1.subscription_service.subscription.repository.PlanRepository;
import com.v1.subscription_service.subscription.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class SubscriptionServiceTest {
    private SubscriptionRepository subscriptionRepository;
    private PlanRepository planRepository;
    private SubscriptionService subscriptionService;

    @BeforeEach
    void setUp() {
        subscriptionRepository = mock(SubscriptionRepository.class);
        planRepository = mock(PlanRepository.class);
        subscriptionService = new SubscriptionService(subscriptionRepository, planRepository);
    }

//    @Test
//    void createSubscription_createsActiveSubscription_whenNoneExists() {
//        SubscriptionRequest request = new SubscriptionRequest();
//        request.setUserId(1L);
//        request.setPlanId(10L);
//
//        Plan plan = new Plan("Pro", 5000, "Pro plan");
//
//        when(planRepository.findById(10L)).thenReturn(Optional.of(plan));
//        when(subscriptionRepository.findByUserIdAndStatus(1L, "ACTIVE"))
//                .thenReturn(Optional.empty());
//        when(subscriptionRepository.save(any(Subscription.class)))
//                .thenAnswer(inv -> inv.getArgument(0));
//
//        SubscriptionDto dto = subscriptionService.createSubscription(request);
//
//        assertThat(dto).isNotNull();
//        assertThat(dto.getUserId()).isEqualTo(1L);
//        assertThat(dto.getPlanId()).isEqualTo(10L);
//        assertThat(dto.getStatus()).isEqualTo("ACTIVE");
//    }

    @Test
    void createSubscription_throwsIfActiveSubscriptionExists() {
        SubscriptionRequest request = new SubscriptionRequest();
        request.setUserId(1L);
        request.setPlanId(10L);

        when(subscriptionRepository.findByUserIdAndStatus(1L, "ACTIVE"))
                .thenReturn(Optional.of(mock(Subscription.class)));

        assertThatThrownBy(() -> subscriptionService.createSubscription(request))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void cancelSubscription_updatesStatusAndCanceledOn() {
        Plan plan = new Plan("Pro", 5000, "Pro plan");
        Subscription existing = new Subscription(
                1L,
                plan,
                "ACTIVE",
                LocalDate.of(2025, 12, 1),
                LocalDate.of(2025, 12, 1),
                LocalDate.of(2025, 12, 31)
        );
        existing.setCanceledOn(null);

        when(subscriptionRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(subscriptionRepository.save(any(Subscription.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        LocalDate cancelDate = LocalDate.of(2025, 12, 15);
        SubscriptionDto dto = subscriptionService.cancelSubscription(5L, cancelDate);

        assertThat(dto.getStatus()).isEqualTo("CANCELED");
        assertThat(dto.getCanceledOn()).isEqualTo(cancelDate);
    }
}
