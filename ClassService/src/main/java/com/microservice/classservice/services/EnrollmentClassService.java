package com.microservice.classservice.services;

import com.microservice.classservice.dtos.CourseResponse;
import com.microservice.classservice.dtos.EnrollmentClassRequest;
import com.microservice.classservice.dtos.EnrollmentClassResponse;
import com.microservice.classservice.models.EnrollmentClass;
import com.microservice.classservice.reponsitories.EnrollmentClassRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentClassService {
    @Autowired
    private EnrollmentClassRepository enrollmentClassRepository;

    @Autowired
    private WebClient.Builder loadBalancedWebClientBuilder;

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
            CourseResponse courseResponse = getCourseById(enrollmentClass.getCourseId());
            enrollmentClassResponse.setId(enrollmentClass.getId());
            enrollmentClassResponse.setCourseName(courseResponse.getName());
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
        CourseResponse courseResponse = getCourseById(enrollmentClass.getCourseId());
        EnrollmentClassResponse enrollmentClassResponse = new EnrollmentClassResponse();
        enrollmentClassResponse.setId(enrollmentClass.getId());
        enrollmentClassResponse.setCourseName(courseResponse.getName());
        enrollmentClassResponse.setClassName(enrollmentClass.getClassName());
        enrollmentClassResponse.setCurrentStudents(enrollmentClass.getCurrentStudents());
        enrollmentClassResponse.setMaxStudent(enrollmentClass.getMaxStudent());
        enrollmentClassResponse.setStatusEnrollClass(enrollmentClass.getStatusEnrollClass());
        return enrollmentClassResponse;
    }
    @Transactional
    public void updateNumberCurrentStudent(String enrollmentClassId) {
        enrollmentClassRepository.incrementCurrentStudents(enrollmentClassId);
    }

    private CourseResponse getCourseById(String courseId) {
        String baseUrl = "http://COURSESERVICE/course/by-courseId";
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("courseId", courseId)
                .toUriString();
        return loadBalancedWebClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CourseResponse.class)
                .block();
    }

}
