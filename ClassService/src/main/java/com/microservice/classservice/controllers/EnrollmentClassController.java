package com.microservice.classservice.controllers;

import com.microservice.classservice.dtos.EnrollmentClassRequest;
import com.microservice.classservice.dtos.EnrollmentClassResponse;
import com.microservice.classservice.services.EnrollmentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class/enrollmentclass")
public class EnrollmentClassController {
    @Autowired
    private EnrollmentClassService enrollmentClassService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEnrollmentClass(@RequestBody EnrollmentClassRequest enrollmentClassRequest){
        enrollmentClassService.createEnrollmentClass(enrollmentClassRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EnrollmentClassResponse> getAllEnrollmentClass(){
        return enrollmentClassService.getAll();
    }

    @GetMapping("/by-course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public List<EnrollmentClassResponse> getEnrollClassByCourse(@PathVariable("courseId") String courseId){
        return enrollmentClassService.getListEnrollClassByCourse(courseId);
    }

    @GetMapping("/by-id/{enrollmentClassId}")
    @ResponseStatus(HttpStatus.OK)
    public EnrollmentClassResponse getEnrollClassById(@PathVariable("enrollmentClassId") String enrollmentClassId){
        return enrollmentClassService.getEnrollClassById(enrollmentClassId);
    }
}
