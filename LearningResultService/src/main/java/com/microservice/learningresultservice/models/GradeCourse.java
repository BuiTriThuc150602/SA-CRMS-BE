package com.microservice.learningresultservice.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "studentgrades")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GradeCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_course_id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "student_grade_id", nullable = false)
    private StudentGrade studentGrade;
    @Column(name = "course_id ")
    private long courseId;
    @Column(name = "semester")
    private String semester;
    @Column(name = "regular_grade")
    private float regularGrade;
    @Column(name = "midterm_grade")
    private float midtermGrade;
    @Column(name = "practical_grade")
    private float practicalGrade;
    @Column(name = "final_grade")
    private float finalGrade;
}
