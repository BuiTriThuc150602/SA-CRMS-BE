package com.microservice.enrollmentservice.services;

import com.microservice.enrollmentservice.dtos.EnrollmentRequest;
import com.microservice.enrollmentservice.dtos.EnrollmentResponse;
import com.microservice.enrollmentservice.models.Enrollment;
import com.microservice.enrollmentservice.reponsitories.EnrollmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;


    public void addEnrollment(EnrollmentRequest enrollmentRequest) {
        Enrollment enrollment = new Enrollment();
        // Lấy thông tin xác thực của người dùng
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String studentId = authentication.getName();
        enrollment.setStudentId(1l);//Sửa chỗ này nha!
        enrollment.setCourseId(enrollmentRequest.getCourseId());
        enrollment.setSemester(enrollmentRequest.getSemester());
        enrollment.setStatus(enrollmentRequest.getStatus());
        enrollment.setRegistrationDate(enrollmentRequest.getRegistrationDate());
        enrollment.setCancellationDate(enrollmentRequest.getCancellationDate());
        enrollment.setClassId(enrollmentRequest.getClassId());
        enrollment.setTeacherId(enrollmentRequest.getTeacherId());
        enrollment.setTuitionFee(enrollmentRequest.getTuitionFee());

        enrollmentRepository.save(enrollment);
        log.info("Enrollment with id " + enrollment.getId() + " is saved");
    }

    public List<EnrollmentResponse> getAll() {
        List<Enrollment> enrollmentList = enrollmentRepository.findAll();

        return enrollmentList.stream().map(enrollment -> {
            EnrollmentResponse enrollmentResponse = new EnrollmentResponse();

            enrollmentResponse.setId(enrollment.getId());
            enrollmentResponse.setStudentId(enrollment.getStudentId());
            enrollmentResponse.setCourseId(enrollment.getCourseId());
            enrollmentResponse.setSemester(enrollment.getSemester());
            enrollmentResponse.setStatus(enrollment.getStatus());
            enrollmentResponse.setRegistrationDate(enrollment.getRegistrationDate());
            enrollmentResponse.setCancellationDate(enrollment.getCancellationDate());
            enrollmentResponse.setClassId(enrollment.getClassId());
            enrollmentResponse.setTeacherId(enrollment.getTeacherId());
            enrollmentResponse.setTuitionFee(enrollment.getTuitionFee());
            return enrollmentResponse;
        }).collect(Collectors.toList());
    }
}
