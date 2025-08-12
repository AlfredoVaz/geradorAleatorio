package com.geradorAleatorio.controller;

import com.geradorAleatorio.dto.RandomActivityDto;
import com.geradorAleatorio.service.RandomActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/random")
public class RandomActivityController {

    private final RandomActivityService activityService;

    public RandomActivityController(RandomActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping()
    public ResponseEntity<RandomActivityDto> getRandomActivity() {
        RandomActivityDto response = activityService.getRandomActivity();
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }
}


