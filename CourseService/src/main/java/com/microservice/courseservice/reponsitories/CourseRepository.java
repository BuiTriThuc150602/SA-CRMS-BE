package com.microservice.courseservice.reponsitories;

import com.microservice.courseservice.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}