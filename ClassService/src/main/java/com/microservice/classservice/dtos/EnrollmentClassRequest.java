package com.microservice.classservice.dtos;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentClassRequest {
    String className;
    long instructorId;
    int currentStudents;
    int maxStudent;
}