package com.v1.subscription_service.subscription.repository;
import com.v1.subscription_service.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserIdAndStatus(Long userId, String status);

    List<Subscription> findByUserId(Long userId);
}