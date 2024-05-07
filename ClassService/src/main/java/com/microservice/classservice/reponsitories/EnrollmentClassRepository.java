package com.microservice.classservice.reponsitories;

import com.microservice.classservice.models.EnrollmentClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentClassRepository extends JpaRepository<EnrollmentClass, Long> {
}