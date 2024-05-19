package com.microservice.courseservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CourseRequest {
    private String id;
    private String name;
    private int credit;
    private String semester;
    private String faculty;
    private List<String> prerequisiteIds;
}
