package com.v1.subscription_service.subscription.service;

import com.v1.subscription_service.subscription.api.PlanDto;
import com.v1.subscription_service.subscription.model.Plan;
import com.v1.subscription_service.subscription.repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanService {
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Transactional
    public PlanDto createPlan(PlanDto dto) {
        Plan plan = new Plan(
                dto.getName(),
                dto.getPriceMonthlyCents(),
                dto.getDescription()
        );
        plan.setActive(dto.getActive() == null ? true : dto.getActive());
        Plan saved = planRepository.save(plan);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<PlanDto> getActivePlans() {
        return planRepository.findAll().stream()
                .filter(Plan::getActive)
                .map(this::toDto)
                .toList();
    }

    private PlanDto toDto(Plan plan) {
        return new PlanDto(
                plan.getId(),
                plan.getName(),
                plan.getPriceMonthlyCents(),
                plan.getDescription(),
                plan.getActive()
        );
    }
}
