package com.microservice.enrollmentservice.dtos;


import com.microservice.enrollmentservice.enums.StatusEnrollClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentClassResponse {
    private String id;
    private  String courseName;
    private  String className;
    private int currentStudents;
    private int maxStudent;
    private StatusEnrollClass statusEnrollClass;
}