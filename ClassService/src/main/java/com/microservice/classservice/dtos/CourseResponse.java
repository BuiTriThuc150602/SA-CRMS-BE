package com.microservice.classservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CourseResponse {
    private String id;
    private String name;
    private int credit;
    private String semester;
    private String faculty;
    private List<String> prerequisiteCourseIds; // Danh sách các ID của môn học tiên quyết
}
