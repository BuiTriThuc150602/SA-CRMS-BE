package com.microservice.scheduleservice.dtos;

import com.microservice.scheduleservice.enums.CollectionStatus;
import com.microservice.scheduleservice.enums.EnrollmentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EnrollmentResponse {
    private String classId;
    private String courseName;
    private String className;
    private String scheduleId;
    private String semester;
    private int credit;
    private LocalDate deadline;
    private EnrollmentStatus enrollmentStatus;
    private CollectionStatus collectionStatus;
    private Date registrationDate;
    private Date cancellationDate;
    private Double tuitionFee;
}
