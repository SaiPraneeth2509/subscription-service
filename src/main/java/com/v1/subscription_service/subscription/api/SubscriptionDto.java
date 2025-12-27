package com.v1.subscription_service.subscription.api;

import java.time.LocalDate;

public class SubscriptionDto {
    private Long id;
    private Long userId;
    private Long planId;
    private String status;
    private LocalDate startedOn;
    private LocalDate canceledOn;
    private LocalDate currentPeriodStart;
    private LocalDate currentPeriodEnd;

    public SubscriptionDto() {
    }

    public SubscriptionDto(Long id,
                           Long userId,
                           Long planId,
                           String status,
                           LocalDate startedOn,
                           LocalDate canceledOn,
                           LocalDate currentPeriodStart,
                           LocalDate currentPeriodEnd) {
        this.id = id;
        this.userId = userId;
        this.planId = planId;
        this.status = status;
        this.startedOn = startedOn;
        this.canceledOn = canceledOn;
        this.currentPeriodStart = currentPeriodStart;
        this.currentPeriodEnd = currentPeriodEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(LocalDate startedOn) {
        this.startedOn = startedOn;
    }

    public LocalDate getCanceledOn() {
        return canceledOn;
    }

    public void setCanceledOn(LocalDate canceledOn) {
        this.canceledOn = canceledOn;
    }

    public LocalDate getCurrentPeriodStart() {
        return currentPeriodStart;
    }

    public void setCurrentPeriodStart(LocalDate currentPeriodStart) {
        this.currentPeriodStart = currentPeriodStart;
    }

    public LocalDate getCurrentPeriodEnd() {
        return currentPeriodEnd;
    }

    public void setCurrentPeriodEnd(LocalDate currentPeriodEnd) {
        this.currentPeriodEnd = currentPeriodEnd;
    }
}
