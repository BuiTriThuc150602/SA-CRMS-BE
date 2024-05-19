package com.microservice.enrollmentservice.dtos;

import com.microservice.enrollmentservice.enums.CollectionStatus;
import com.microservice.enrollmentservice.enums.EnrollmentStatus;
import com.microservice.enrollmentservice.enums.StatusEnrollClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EnrollmentResponse {
    private String id;
    private String classId;
    private String courseName;
    private String className;
    private String scheduleId;
    private String semester;
    private int credit;
    private String groupPractice;
    private LocalDate deadline;
    private EnrollmentStatus enrollmentStatus;
    private CollectionStatus collectionStatus;
    private StatusEnrollClass statusEnrollClass;
    private Date registrationDate;
    private Date cancellationDate;
    private Double tuitionFee;
}
