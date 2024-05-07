package com.microservice.enrollmentservice.dtos;

import com.microservice.enrollmentservice.enums.EnrollmentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EnrollmentRequest {
    private Long courseId;
    private String semester;
    private EnrollmentStatus status;
    private Date registrationDate;
    private Date cancellationDate;
    private Long classId;
    private Long teacherId;
    private Double tuitionFee;
}
