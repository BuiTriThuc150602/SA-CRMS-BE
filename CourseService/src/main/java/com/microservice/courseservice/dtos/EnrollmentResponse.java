package com.microservice.courseservice.dtos;

import com.microservice.courseservice.enums.CollectionStatus;
import com.microservice.courseservice.enums.EnrollmentStatus;
import com.microservice.courseservice.enums.StatusEnrollClass;
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
    private String courseId;
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
