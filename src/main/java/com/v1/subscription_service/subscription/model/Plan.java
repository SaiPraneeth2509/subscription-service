package com.v1.subscription_service.subscription.model;
import jakarta.persistence.*;

@Entity
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "price_monthly_cents", nullable = false)
    private Integer priceMonthlyCents;

    @Column
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

    protected Plan(){
        //for JPA
    }

    public Plan(String name, Integer priceMonthlyCents, String description) {
        this.name = name;
        this.priceMonthlyCents = priceMonthlyCents;
        this.description = description;
        this.active = true;
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
