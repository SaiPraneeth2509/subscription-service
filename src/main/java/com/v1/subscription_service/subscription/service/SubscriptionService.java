package com.v1.subscription_service.subscription.service;

import com.v1.subscription_service.subscription.api.SubscriptionDto;
import com.v1.subscription_service.subscription.api.SubscriptionRequest;
import com.v1.subscription_service.subscription.model.Plan;
import com.v1.subscription_service.subscription.model.Subscription;
import com.v1.subscription_service.subscription.repository.PlanRepository;
import com.v1.subscription_service.subscription.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SubscriptionService {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);

    private static final String STATUS_ACTIVE = "ACTIVE";
    private static final String STATUS_CANCELED = "CANCELED";

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    @Transactional
    public SubscriptionDto createSubscription(SubscriptionRequest request) {
        // Simple rule: one active subscription per user
        subscriptionRepository.findByUserIdAndStatus(request.getUserId(), STATUS_ACTIVE)
                .ifPresent(existing -> {
                    throw new IllegalStateException("User already has an active subscription");
                });

        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new NoSuchElementException("Plan not found"));

        LocalDate startDate = request.getStartDate() != null
                ? request.getStartDate()
                : LocalDate.now();

        LocalDate periodStart = startDate.withDayOfMonth(1);
        LocalDate periodEnd = startDate.with(TemporalAdjusters.lastDayOfMonth());

        Subscription subscription = new Subscription(
                request.getUserId(),
                plan,
                STATUS_ACTIVE,
                startDate,
                periodStart,
                periodEnd
        );

        Subscription saved = subscriptionRepository.save(subscription);
        log.info("metric=subscriptions_created_total value=1 userId={} planId={}",
                saved.getUserId(), saved.getPlan().getId());
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public SubscriptionDto getActiveSubscriptionForUser(Long userId) {
        Subscription sub = subscriptionRepository.findByUserIdAndStatus(userId, STATUS_ACTIVE)
                .orElseThrow(() -> new NoSuchElementException("Active subscription not found"));
        return toDto(sub);
    }

    @Transactional
    public SubscriptionDto cancelSubscription(Long subscriptionId, LocalDate cancelDate) {
        Subscription sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NoSuchElementException("Subscription not found"));

        if (!STATUS_ACTIVE.equals(sub.getStatus())) {
            throw new IllegalStateException("Subscription is not active");
        }

        LocalDate effectiveCancelDate = cancelDate != null ? cancelDate : LocalDate.now();

        sub.setStatus(STATUS_CANCELED);
        sub.setCanceledOn(effectiveCancelDate);
        // For v1 we keep currentPeriodStart/End as originally set

        Subscription saved = subscriptionRepository.save(sub);
        return toDto(saved);
    }

    private SubscriptionDto toDto(Subscription sub) {
        return new SubscriptionDto(
                sub.getId(),
                sub.getUserId(),
                sub.getPlan().getId(),
                sub.getStatus(),
                sub.getStartedOn(),
                sub.getCanceledOn(),
                sub.getCurrentPeriodStart(),
                sub.getCurrentPeriodEnd()
        );
    }
}
