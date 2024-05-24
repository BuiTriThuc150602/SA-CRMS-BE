package com.microservice.enrollmentservice.models;

import com.microservice.enrollmentservice.enums.CollectionStatus;
import com.microservice.enrollmentservice.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "enrollments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Enrollment {
    @Id
    @Column(name = "enrollment_id")
    private String id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "class_id")
    private String classId;

    @Column(name = "schedule_id")
    private String scheduleId;

    private String semester;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "enrollment_status")
    private EnrollmentStatus enrollmentStatus;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "collection_status")
    private CollectionStatus collectionStatus;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "cancellation_date")
    private Date cancellationDate;

    @Column(name = "tuition_fee")
    private Double tuitionFee;

}
