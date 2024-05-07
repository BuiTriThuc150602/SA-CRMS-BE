package com.microservice.scheduleservice.controllers;

import com.microservice.scheduleservice.dtos.ScheduleRequest;
import com.microservice.scheduleservice.dtos.ScheduleResponse;
import com.microservice.scheduleservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        scheduleService.createSchedule(scheduleRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ScheduleResponse> getAllSchedule() {
        return scheduleService.getAll();
    }
}
