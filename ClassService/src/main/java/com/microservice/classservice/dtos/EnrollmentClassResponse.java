package com.microservice.classservice.dtos;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentClassResponse {
    long id;
    String className;
    long instructorId;
    int currentStudents;
    int maxStudent;
}