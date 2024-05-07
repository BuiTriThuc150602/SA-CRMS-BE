package com.microservice.enrollmentservice.controllers;

import com.microservice.enrollmentservice.dtos.EnrollmentRequest;
import com.microservice.enrollmentservice.dtos.EnrollmentResponse;
import com.microservice.enrollmentservice.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEnrollment(@RequestBody EnrollmentRequest enrollmentRequest){
        enrollmentService.addEnrollment(enrollmentRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EnrollmentResponse> getAllEnrollments(){
        return enrollmentService.getAll();
    }
}
