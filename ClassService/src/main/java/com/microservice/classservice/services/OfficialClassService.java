package com.microservice.classservice.services;

import com.microservice.classservice.models.OfficialClass;
import com.microservice.classservice.reponsitories.OfficialClassRepository;
import com.microservice.classservice.dtos.OfficialClassRequest;
import com.microservice.classservice.dtos.OfficialClassResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficialClassService {
    @Autowired
    private OfficialClassRepository officialClassRepository;

    public void createOfficialClass(OfficialClassRequest officialClassRequest) {
        OfficialClass officialClass = new OfficialClass();
        officialClass.setClassName(officialClassRequest.getClassName());
        officialClass.setInstructorId(officialClassRequest.getInstructorId());
        officialClass.setRegistrationDate(officialClassRequest.getRegistrationDate());
        officialClassRepository.save(officialClass);
    }

    public List<OfficialClassResponse> getAll() {
        List<OfficialClass> officialClasses = officialClassRepository.findAll();

        List<OfficialClassResponse> officialClassResponses = officialClasses.stream().map(officialClass -> {
            OfficialClassResponse officialClassResponse = new OfficialClassResponse();
            officialClassResponse.setId(officialClass.getId());
            officialClassResponse.setClassName(officialClass.getClassName());
            officialClassResponse.setInstructorId(officialClass.getInstructorId());
            officialClassResponse.setRegistrationDate(officialClass.getRegistrationDate());
            return officialClassResponse;
        }).collect(Collectors.toList());
        return officialClassResponses;
    }
}
