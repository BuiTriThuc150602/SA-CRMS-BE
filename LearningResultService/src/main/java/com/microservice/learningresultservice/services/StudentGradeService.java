package com.microservice.learningresultservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentGradeService {
    @Autowired
    private StudentGradeRepository studentGradeRepositor;
    @Autowired
    private GradeCourseRepository gradeCourseRepository;
}
