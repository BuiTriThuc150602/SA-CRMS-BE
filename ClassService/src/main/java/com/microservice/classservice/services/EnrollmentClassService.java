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

    public void createEnrollmentClass(EnrollmentClassRequest enrollmentClassRequest) {
        EnrollmentClass enrollmentClass = new EnrollmentClass();
        enrollmentClass.setId(enrollmentClassRequest.getId());
        enrollmentClass.setCourseId(enrollmentClassRequest.getCourseId());
        enrollmentClass.setClassName(enrollmentClassRequest.getClassName());
        enrollmentClass.setCurrentStudents(enrollmentClassRequest.getCurrentStudents());
        enrollmentClass.setMaxStudent(enrollmentClassRequest.getMaxStudent());
        enrollmentClass.setStatusEnrollClass(enrollmentClassRequest.getStatusEnrollClass());
        enrollmentClassRepository.save(enrollmentClass);
    }

    public List<EnrollmentClassResponse> getAll() {
        List<EnrollmentClass> enrollmentClassList = enrollmentClassRepository.findAll();

        List<EnrollmentClassResponse> enrollmentClassResponses = enrollmentClassList.stream().map(enrollmentClass -> {
            EnrollmentClassResponse enrollmentClassResponse = new EnrollmentClassResponse();
            enrollmentClassResponse.setId(enrollmentClass.getId());
            enrollmentClassResponse.setCourseName(enrollmentClass.getCourseId());
            enrollmentClassResponse.setClassName(enrollmentClass.getClassName());
            enrollmentClassResponse.setCurrentStudents(enrollmentClass.getCurrentStudents());
            enrollmentClassResponse.setMaxStudent(enrollmentClass.getMaxStudent());
            enrollmentClassResponse.setStatusEnrollClass(enrollmentClass.getStatusEnrollClass());
            return enrollmentClassResponse;
        }).collect(Collectors.toList());
        return enrollmentClassResponses;
    }

    public List<EnrollmentClassResponse> getListEnrollClassByCourse(String courseId) {
        List<EnrollmentClass> enrollmentClassList = enrollmentClassRepository.findByCoureId(courseId);
        List<EnrollmentClassResponse> enrollmentClassResponses = enrollmentClassList.stream().map(enrollmentClass -> {
            EnrollmentClassResponse enrollmentClassResponse = new EnrollmentClassResponse();
            enrollmentClassResponse.setId(enrollmentClass.getId());
            enrollmentClassResponse.setCourseName(enrollmentClass.getCourseId());
            enrollmentClassResponse.setClassName(enrollmentClass.getClassName());
            enrollmentClassResponse.setCurrentStudents(enrollmentClass.getCurrentStudents());
            enrollmentClassResponse.setMaxStudent(enrollmentClass.getMaxStudent());
            enrollmentClassResponse.setStatusEnrollClass(enrollmentClass.getStatusEnrollClass());
            return enrollmentClassResponse;
        }).collect(Collectors.toList());
        return enrollmentClassResponses;
    }

    public EnrollmentClassResponse getEnrollClassById(String enrollmentClassId) {
        EnrollmentClass enrollmentClass = enrollmentClassRepository.findById(enrollmentClassId).get();
        EnrollmentClassResponse enrollmentClassResponse = new EnrollmentClassResponse();
        enrollmentClassResponse.setId(enrollmentClass.getId());
        enrollmentClassResponse.setCourseName(enrollmentClass.getCourseId());
        enrollmentClassResponse.setClassName(enrollmentClass.getClassName());
        enrollmentClassResponse.setCurrentStudents(enrollmentClass.getCurrentStudents());
        enrollmentClassResponse.setMaxStudent(enrollmentClass.getMaxStudent());
        enrollmentClassResponse.setStatusEnrollClass(enrollmentClass.getStatusEnrollClass());
        return enrollmentClassResponse;
    }
}
