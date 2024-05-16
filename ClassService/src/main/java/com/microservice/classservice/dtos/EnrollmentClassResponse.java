package com.microservice.classservice.dtos;

import com.microservice.classservice.enums.StatusEnrollClass;
import lombok.*;

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