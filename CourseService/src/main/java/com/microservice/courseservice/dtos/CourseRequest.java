package com.microservice.courseservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CourseRequest {
    private String name;
    private int credit;
    private List<Long> prerequisiteIds;
}
