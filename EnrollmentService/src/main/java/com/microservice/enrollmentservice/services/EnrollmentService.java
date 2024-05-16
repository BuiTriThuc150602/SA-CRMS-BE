package com.microservice.enrollmentservice.services;

import com.microservice.enrollmentservice.dtos.*;
import com.microservice.enrollmentservice.models.Enrollment;
import com.microservice.enrollmentservice.reponsitories.EnrollmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnrollmentService {
    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(WebClient.Builder loadBalancedWebClientBuilder, EnrollmentRepository enrollmentRepository) {
        this.loadBalancedWebClientBuilder = loadBalancedWebClientBuilder;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void addEnrollment(EnrollmentRequest enrollmentRequest) {
        Enrollment enrollment = new Enrollment();
        // Lấy thông tin xác thực của người dùng
        enrollment.setId(enrollmentRequest.getId());
        enrollment.setStudentId(enrollmentRequest.getStudentId());//Sửa chỗ này nha!
        enrollment.setCourseId(enrollmentRequest.getCourseId());
        enrollment.setClassId(enrollmentRequest.getClassId());
        enrollment.setScheduleId(enrollmentRequest.getScheduleId());
        enrollment.setSemester(enrollmentRequest.getSemester());
        enrollment.setDeadline(enrollmentRequest.getDeadline());
        enrollment.setEnrollmentStatus(enrollmentRequest.getEnrollmentStatus());
        enrollment.setCollectionStatus(enrollmentRequest.getCollectionStatus());
        enrollment.setRegistrationDate(enrollmentRequest.getRegistrationDate());
        enrollment.setCancellationDate(enrollmentRequest.getCancellationDate());
        enrollment.setTuitionFee(enrollmentRequest.getTuitionFee());

        enrollmentRepository.save(enrollment);
        log.info("Enrollment with id " + enrollment.getId() + " is saved");
    }

    public List<EnrollmentResponse> getEnrollmentsByStudentIdAndSemester(String studentId, String semester) {
        List<Enrollment> enrollmentList = enrollmentRepository.findEnrollmentsByStudentIdAndSemester(studentId, semester);

        return enrollmentList.stream().map(enrollment -> {
            EnrollmentResponse enrollmentResponse = new EnrollmentResponse();
            CourseResponse courseResponse = getCourseById(enrollment.getCourseId());
            EnrollmentClassResponse enrollmentClassResponse = getEnrollmentClassById(enrollment.getClassId());
            ScheduleResponse scheduleResponse = getScheduleById(enrollment.getScheduleId());

            enrollmentResponse.setClassId(enrollment.getClassId());
            //Tim môn học theo Id bằng WebClient
            enrollmentResponse.setCourseName(courseResponse.getName());
            enrollmentResponse.setCredit(courseResponse.getCredit());
            //Tim lớp HP theo Id bằng WebClient
            enrollmentResponse.setClassName(enrollmentClassResponse.getClassName());
            enrollmentResponse.setSemester(enrollment.getSemester());
            enrollmentResponse.setDeadline(enrollment.getDeadline());
            enrollmentResponse.setEnrollmentStatus(enrollment.getEnrollmentStatus());
            enrollmentResponse.setCollectionStatus(enrollment.getCollectionStatus());
            enrollmentResponse.setRegistrationDate(enrollment.getRegistrationDate());
            enrollmentResponse.setCancellationDate(enrollment.getCancellationDate());
            enrollmentResponse.setTuitionFee(enrollment.getTuitionFee());
            return enrollmentResponse;
        }).collect(Collectors.toList());
    }


    public List<EnrollmentResponse> getAll() {
        List<Enrollment> enrollmentList = enrollmentRepository.findAll();

        return enrollmentList.stream().map(enrollment -> {
            EnrollmentResponse enrollmentResponse = new EnrollmentResponse();
            CourseResponse courseResponse = getCourseById(enrollment.getCourseId());
            EnrollmentClassResponse enrollmentClassResponse = getEnrollmentClassById(enrollment.getClassId());
            ScheduleResponse scheduleResponse = getScheduleById(enrollment.getScheduleId());

            enrollmentResponse.setClassId(enrollment.getClassId());
            //Tim môn học theo Id bằng WebClient
            enrollmentResponse.setCourseName(courseResponse.getName());
            enrollmentResponse.setCredit(courseResponse.getCredit());
            //Tim lớp HP theo Id bằng WebClient
            enrollmentResponse.setClassName(enrollmentClassResponse.getClassName());
            enrollmentResponse.setSemester(enrollment.getSemester());
            enrollmentResponse.setDeadline(enrollment.getDeadline());
            enrollmentResponse.setEnrollmentStatus(enrollment.getEnrollmentStatus());
            enrollmentResponse.setCollectionStatus(enrollment.getCollectionStatus());
            enrollmentResponse.setRegistrationDate(enrollment.getRegistrationDate());
            enrollmentResponse.setCancellationDate(enrollment.getCancellationDate());
            enrollmentResponse.setTuitionFee(enrollment.getTuitionFee());
            return enrollmentResponse;
        }).collect(Collectors.toList());
    }

    private CourseResponse getCourseById(String courseId){
        return loadBalancedWebClientBuilder.build()
                .get()
                .uri("http://COURSESERVICE/course/{courseId}", courseId)
                .retrieve()
                .bodyToMono(CourseResponse.class)
                .block();
    }

    private EnrollmentClassResponse getEnrollmentClassById(String enrollmentClassId){
        return loadBalancedWebClientBuilder.build()
                .get()
                .uri("http://CLASSSERVICE/class/enrollmentclass/by-id/{enrollmentClassId}", enrollmentClassId)
                .retrieve()
                .bodyToMono(EnrollmentClassResponse.class)
                .block();
    }

    private ScheduleResponse getScheduleById(String scheduleId){
        return loadBalancedWebClientBuilder.build()
                .get()
                .uri("http://SCHEDULESERVICE/schedule/by-id/{scheduleId}", scheduleId)
                .retrieve()
                .bodyToMono(ScheduleResponse.class)
                .block();
    }
}
