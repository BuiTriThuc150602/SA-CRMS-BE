package com.microservice.learningresultservice.reponsitories;

import com.microservice.learningresultservice.models.GradeCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeCourseRepository extends JpaRepository<GradeCourse, String> {
}