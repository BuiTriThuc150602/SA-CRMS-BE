package com.microservice.enrollmentservice.models;

import com.microservice.enrollmentservice.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "semester")
    private String semester;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private EnrollmentStatus status;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "cancellation_date")
    private Date cancellationDate;

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "tuition_fee")
    private Double tuitionFee;

    public Enrollment(Long studentId, Long courseId, String semester, EnrollmentStatus status, Date registrationDate, Date cancellationDate, Long classId, Long teacherId, Double tuitionFee) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
        this.status = status;
        this.registrationDate = registrationDate;
        this.cancellationDate = cancellationDate;
        this.classId = classId;
        this.teacherId = teacherId;
        this.tuitionFee = tuitionFee;
    }
}
