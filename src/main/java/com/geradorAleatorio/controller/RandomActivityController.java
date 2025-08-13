package com.geradorAleatorio.controller;

import com.geradorAleatorio.dto.RandomActivityDto;
import com.geradorAleatorio.service.RandomActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/randomActivities")
public class RandomActivityController {

    private final RandomActivityService activityService;

    public RandomActivityController(RandomActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping()
    public ResponseEntity<RandomActivityDto> getRandomActivity() {
        RandomActivityDto response = activityService.getRandomActivity();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<RandomActivityDto>> listActivities() {
        List<RandomActivityDto> activities = activityService.listActivities();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/list/{key}")
    public ResponseEntity<RandomActivityDto> findActivityByKey(@PathVariable String key) {
        RandomActivityDto activity = activityService.findActivityByKey(key);
        return ResponseEntity.ok(activity);
    }
}


