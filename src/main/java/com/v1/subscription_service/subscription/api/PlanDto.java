package com.v1.subscription_service.subscription.api;

public class PlanDto {

    private Long id;
    private String name;
    private Integer priceMonthlyCents;
    private String description;
    private Boolean active;

    public PlanDto() {
    }

    public PlanDto(Long id, String name, Integer priceMonthlyCents,
                   String description, Boolean active) {
        this.id = id;
        this.name = name;
        this.priceMonthlyCents = priceMonthlyCents;
        this.description = description;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriceMonthlyCents() {
        return priceMonthlyCents;
    }

    public void setPriceMonthlyCents(Integer priceMonthlyCents) {
        this.priceMonthlyCents = priceMonthlyCents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
