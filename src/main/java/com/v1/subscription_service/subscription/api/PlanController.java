package com.v1.subscription_service.subscription.api;

import com.v1.subscription_service.subscription.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/plans")
public class PlanController {
    private static final Logger log = LoggerFactory.getLogger(PlanController.class);

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanDto> getPlans() {
        log.info("GET /plans");
        return planService.getActivePlans();
    }

    @PostMapping
    public ResponseEntity<PlanDto> createPlan(@RequestBody PlanDto request) {
        log.info("POST /plans name={} priceMonthlyCents={}",
                request.getName(), request.getPriceMonthlyCents());
        PlanDto created = planService.createPlan(request);
        log.info("Plan created id={}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
