package com.microservice.enrollmentservice.dtos;

import com.microservice.enrollmentservice.enums.CollectionStatus;
import com.microservice.enrollmentservice.enums.EnrollmentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EnrollmentRequest {
    private String id;
    private String studentId;
    private String courseId;
    private String classId;
    private String scheduleId;
    private LocalDate deadline;
    private EnrollmentStatus enrollmentStatus;
    private CollectionStatus collectionStatus;
    private Date registrationDate;
    private Date cancellationDate;
    private Double tuitionFee;
}
