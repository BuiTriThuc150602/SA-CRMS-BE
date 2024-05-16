package com.microservice.learningresultservice.models;

import com.microservice.learningresultservice.enums.ClassificationEnum;
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
public class GradeCourse {
    @Id
    @Column(name = "grade_course_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "student_grade_id", nullable = false)
    private StudentGrade studentGrade;
    @Column(name = "enrollment_class_id")
    private String enrollmentClassId;
    @ElementCollection
    @CollectionTable(name = "regular_grades", joinColumns = @JoinColumn(name = "grade_course_id"))
    @Column(name = "prerequisite_id")
    private List<Float> regularGrades;
    @Column(name = "midterm_grade")
    private float midtermGrade;
    @Column(name = "practical_grade")
    private float practicalGrade;
    @Column(name = "end_of_term_grade")
    private float endOfTermGrade;
    @Column(name = "final_grade")
    private float finalGrade;
    @Column(name = "scale")
    private float scale;
    @Column(name = "letter_grade")
    private String letterGrade;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "classification")
    private ClassificationEnum classification;
    @Column(name = "note")
    private String note;
    @Column(name = "goal")
    private String goal;
}
