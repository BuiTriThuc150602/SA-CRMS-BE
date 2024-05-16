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
    @Column(name = "course_id", nullable = false)
    private String id;
    @Column(name = "course_name", nullable = false)
    private String name;
    private int credit;
    private String semester;
    private String faculty;
    @ElementCollection
    @CollectionTable(name = "course_prerequisites", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "prerequisite_id")
    private List<Long> prerequisiteIds;

}
