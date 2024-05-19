package com.microservice.scheduleservice.controllers;

import com.microservice.scheduleservice.dtos.ApiResponse;
import com.microservice.scheduleservice.dtos.ScheduleDetailResponse;
import com.microservice.scheduleservice.dtos.ScheduleRequest;
import com.microservice.scheduleservice.dtos.ScheduleResponse;
import com.microservice.scheduleservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
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

    @GetMapping("/by-enrollment-class")
    public ResponseEntity<ApiResponse<List<ScheduleResponse>>> getListScheduleByEnrollmentClass(@RequestParam String enrollmentClassId) {
        ApiResponse<List<ScheduleResponse>> response = scheduleService.getListScheduleByEnrollmentClass(enrollmentClassId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @GetMapping("/by-id")
    @ResponseStatus(HttpStatus.OK)
    public ScheduleResponse getScheduleById(@RequestParam String scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    @GetMapping("/by-enrollment")
    @ResponseStatus(HttpStatus.OK)
    public List<ScheduleDetailResponse> getListScheduleByEnrollment(@RequestParam String studentId,
                                                                    @RequestParam String semester) {
        return scheduleService.getListScheduleByEnrollment(studentId, semester);
    }

    @GetMapping("/check-duplicated-schedule")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkDuplicatedSchedule(
            @RequestParam String studentId,
            @RequestParam String semester,
            @RequestParam String scheduleId) {
        return scheduleService.checkDuplicatedSchedule(studentId, semester, scheduleId);
    }
}
