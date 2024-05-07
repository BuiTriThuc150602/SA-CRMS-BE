package com.microservice.learningresultservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "studentgrades")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_grade_id")
    private long id;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "total_credits")
    private int totalCredits;
    @Column(name = "total_credits")
    private int totalCourses;
    @Column(name = "cumulative_gpa")
    private float cumulativeGpa;
    @OneToMany(mappedBy = "studentGrade")
    private List<GradeCourse> gradeCourses;

}
