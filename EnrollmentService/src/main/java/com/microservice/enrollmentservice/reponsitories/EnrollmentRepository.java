package com.microservice.enrollmentservice.reponsitories;

import com.microservice.enrollmentservice.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}