package com.microservice.classservice.services;

import com.microservice.classservice.dtos.EnrollmentClassRequest;
import com.microservice.classservice.dtos.EnrollmentClassResponse;
import com.microservice.classservice.models.EnrollmentClass;
import com.microservice.classservice.reponsitories.EnrollmentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentClassService {
    @Autowired
    private EnrollmentClassRepository enrollmentClassRepository;

    public void createEnrollmentClass(EnrollmentClassRequest enrollmentClassRequest){
        EnrollmentClass enrollmentClass = new EnrollmentClass();
        enrollmentClass.setClassName(enrollmentClassRequest.getClassName());
        enrollmentClass.setInstructorId(enrollmentClassRequest.getInstructorId());
        enrollmentClass.setCurrentStudents(enrollmentClassRequest.getCurrentStudents());
        enrollmentClass.setMaxStudent(enrollmentClassRequest.getMaxStudent());
        enrollmentClassRepository.save(enrollmentClass);
    }

    public List<EnrollmentClassResponse> getAll(){
        List<EnrollmentClass> enrollmentClassList = enrollmentClassRepository.findAll();

        List<EnrollmentClassResponse> enrollmentClassResponses = enrollmentClassList.stream().map(enrollmentClass -> {
            EnrollmentClassResponse enrollmentClassResponse = new EnrollmentClassResponse();
            enrollmentClassResponse.setId(enrollmentClass.getId());
            enrollmentClassResponse.setClassName(enrollmentClass.getClassName());
            enrollmentClassResponse.setInstructorId(enrollmentClass.getInstructorId());
            enrollmentClassResponse.setCurrentStudents(enrollmentClass.getCurrentStudents());
            enrollmentClassResponse.setMaxStudent(enrollmentClass.getMaxStudent());
            return enrollmentClassResponse;
        }).collect(Collectors.toList());
        return  enrollmentClassResponses;
    }
}
