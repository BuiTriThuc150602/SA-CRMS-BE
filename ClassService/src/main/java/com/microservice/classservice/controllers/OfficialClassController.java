package com.microservice.classservice.controllers;

import com.microservice.classservice.dtos.OfficialClassRequest;
import com.microservice.classservice.dtos.OfficialClassResponse;
import com.microservice.classservice.services.OfficialClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class/officalclass")
public class OfficialClassController {
    @Autowired
    private OfficialClassService officialClassService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOfficialClass(@RequestBody OfficialClassRequest officialClassRequest){
        officialClassService.createOfficialClass(officialClassRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OfficialClassResponse> getAllEnrollmentClass(){
        return officialClassService.getAll();
    }
}
