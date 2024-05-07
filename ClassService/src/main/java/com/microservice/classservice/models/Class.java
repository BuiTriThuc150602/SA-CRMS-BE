package com.microservice.classservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clazz")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "class_name")
    private String className;
    @Column(name = "instructor_id")
    private long instructorId;
}
