package com.microservice.classservice.dtos;

import com.microservice.classservice.enums.StatusEnrollClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentClassRequest {
    private String id;
    private  String courseId;
    private  String className;
    private int currentStudents;
    private int maxStudent;
    private StatusEnrollClass statusEnrollClass;
}