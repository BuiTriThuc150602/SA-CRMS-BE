package com.microservice.classservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    @Column(name = "current_students")
    private int currentStudents;

    @Column(name = "max_student")
    private int maxStudent;
}
