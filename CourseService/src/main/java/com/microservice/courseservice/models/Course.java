package com.microservice.courseservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private long id;
    @Column(name = "course_name", nullable = false)
    private String name;
    private int credit;
    @ElementCollection
    @CollectionTable(name = "course_prerequisites", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "prerequisite_id")
    private List<Long> prerequisiteIds;

    public Course(String name, int credit, List<Long> prerequisiteIds) {
        this.name = name;
        this.credit = credit;
        this.prerequisiteIds = prerequisiteIds;
    }
}
