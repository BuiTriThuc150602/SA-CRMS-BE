package com.microservice.enrollmentservice.services;

import com.microservice.enrollmentservice.dtos.*;
import com.microservice.enrollmentservice.models.Enrollment;
import com.microservice.enrollmentservice.reponsitories.EnrollmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

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

            enrollmentResponse.setId(enrollment.getId());
            enrollmentResponse.setCourseId((enrollment.getCourseId()));
            enrollmentResponse.setClassId(enrollment.getClassId());
            //Tim môn học theo Id bằng WebClient
            enrollmentResponse.setCourseName(courseResponse.getName());
            enrollmentResponse.setCredit(courseResponse.getCredit());
            //Tim lớp HP theo Id bằng WebClient
            enrollmentResponse.setClassName(enrollmentClassResponse.getClassName());
            enrollmentResponse.setScheduleId(enrollment.getScheduleId());
            enrollmentResponse.setSemester(enrollment.getSemester());
            enrollmentResponse.setDeadline(enrollment.getDeadline());
            enrollmentResponse.setGroupPractice(scheduleResponse.getPracticeGroup());
            enrollmentResponse.setEnrollmentStatus(enrollment.getEnrollmentStatus());
            enrollmentResponse.setCollectionStatus(enrollment.getCollectionStatus());
            enrollmentResponse.setRegistrationDate(enrollment.getRegistrationDate());
            enrollmentResponse.setCancellationDate(enrollment.getCancellationDate());
            enrollmentResponse.setStatusEnrollClass(enrollmentClassResponse.getStatusEnrollClass());
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
            enrollmentResponse.setCourseId((enrollment.getCourseId()));
            enrollmentResponse.setId(enrollment.getId());
            enrollmentResponse.setClassId(enrollment.getClassId());
            //Tim môn học theo Id bằng WebClient
            enrollmentResponse.setCourseName(courseResponse.getName());
            enrollmentResponse.setCredit(courseResponse.getCredit());
            //Tim lớp HP theo Id bằng WebClient
            enrollmentResponse.setClassName(enrollmentClassResponse.getClassName());
            enrollmentResponse.setScheduleId(enrollment.getScheduleId());
            enrollmentResponse.setSemester(enrollment.getSemester());
            enrollmentResponse.setDeadline(enrollment.getDeadline());
            enrollmentResponse.setGroupPractice(scheduleResponse.getPracticeGroup());
            enrollmentResponse.setEnrollmentStatus(enrollment.getEnrollmentStatus());
            enrollmentResponse.setCollectionStatus(enrollment.getCollectionStatus());
            enrollmentResponse.setRegistrationDate(enrollment.getRegistrationDate());
            enrollmentResponse.setCancellationDate(enrollment.getCancellationDate());
            enrollmentResponse.setStatusEnrollClass(enrollmentClassResponse.getStatusEnrollClass());
            enrollmentResponse.setTuitionFee(enrollment.getTuitionFee());
            return enrollmentResponse;
        }).collect(Collectors.toList());
    }

    private CourseResponse getCourseById(String courseId){
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

    private EnrollmentClassResponse getEnrollmentClassById(String enrollmentClassId) {
        String baseUrl = "http://CLASSSERVICE/class/enrollmentclass/by-id";

        // Sử dụng UriComponentsBuilder để xây dựng URI với tham số yêu cầu
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("enrollmentClassId", enrollmentClassId)
                .toUriString();

        return loadBalancedWebClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(EnrollmentClassResponse.class)
                .block();
    }


    private ScheduleResponse getScheduleById(String scheduleId){
        String baseUrl = "http://SCHEDULESERVICE/schedule/by-id";
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("scheduleId", scheduleId)
                .toUriString();
        return loadBalancedWebClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ScheduleResponse.class)
                .block();
    }


}
