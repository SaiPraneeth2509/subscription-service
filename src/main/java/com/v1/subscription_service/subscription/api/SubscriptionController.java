package com.v1.subscription_service.subscription.api;

import com.v1.subscription_service.subscription.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionDto> createSubscription(
            @RequestBody SubscriptionRequest request) {
        log.info("POST /subscriptions userId={} planId={}",
                request.getUserId(), request.getPlanId());
        SubscriptionDto created = subscriptionService.createSubscription(request);
        log.info("Subscription created id={} userId={} planId={}",
                created.getId(), created.getUserId(), created.getPlanId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/user/{userId}")
    public SubscriptionDto getActiveSubscription(@PathVariable Long userId) {
        log.info("GET /subscriptions/user/{} ", userId);
        return subscriptionService.getActiveSubscriptionForUser(userId);
    }

    @PostMapping("/{id}/cancel")
    public SubscriptionDto cancelSubscription(
            @PathVariable("id") Long subscriptionId,
            @RequestBody(required = false) Map<String, String> body) {

        log.info("POST /subscriptions/{}/cancel", subscriptionId);

        LocalDate cancelDate = null;
        if (body != null && body.containsKey("cancelDate")) {
            cancelDate = LocalDate.parse(body.get("cancelDate"));
        }

        SubscriptionDto dto = subscriptionService.cancelSubscription(subscriptionId, cancelDate);
        log.info("Subscription canceled id={} cancelDate={}", dto.getId(), dto.getCanceledOn());
        return dto;
    }
}
