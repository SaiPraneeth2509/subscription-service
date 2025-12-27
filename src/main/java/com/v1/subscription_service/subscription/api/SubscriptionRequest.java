package com.v1.subscription_service.subscription.api;

import java.time.LocalDate;

public class SubscriptionRequest {
    private Long userId;
    private Long planId;

    private LocalDate startDate;

    public SubscriptionRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
