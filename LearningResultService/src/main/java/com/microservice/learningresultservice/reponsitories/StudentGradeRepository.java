package com.microservice.learningresultservice.reponsitories;

import com.microservice.learningresultservice.models.StudentGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentGradeRepository extends JpaRepository<StudentGrade, String> {
}