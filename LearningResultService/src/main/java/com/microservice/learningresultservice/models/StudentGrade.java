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
public class StudentGrade {
    @Id
    @Column(name = "student_grade_id")
    private String id;
    @Column(name = "student_id")
    private long studentId;
    @Column(name = "semester")
    private String semester;
    @Column(name = "total_credits")
    private int totalCredits;
    @Column(name = "total_credits")
    private int totalCreditsOwed;
    @Column(name = "avg_scrore_10th")
    private float avgScrore10th;
    @Column(name = "avg_scrore_4th")
    private float avgScrore4th;
    @Column(name = "cumulative_gpa_10th")
    private float cumulativeGpa10th;
    @Column(name = "cumulative_gpa_4th")
    private float cumulativeGpa4th;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "total_classification_accumulation")
    private ClassificationEnum totalClassificationAccumulation;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "total_classification_semester")
    private ClassificationEnum totalClassificationSemester;
    @OneToMany(mappedBy = "studentGrade")
    private List<GradeCourse> gradeCourses;

}
