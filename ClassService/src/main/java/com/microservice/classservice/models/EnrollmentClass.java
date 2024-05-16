package com.microservice.classservice.models;

import com.microservice.classservice.enums.StatusEnrollClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enrollment_class")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentClass extends Class{

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "current_students")
    private int currentStudents;

    @Column(name = "max_student")
    private int maxStudent;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_enrollment_class")
    private StatusEnrollClass statusEnrollClass;
}
