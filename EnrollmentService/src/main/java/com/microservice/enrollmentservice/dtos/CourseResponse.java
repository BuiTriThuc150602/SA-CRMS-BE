package com.microservice.enrollmentservice.dtos;

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
    private List<Long> prerequisiteCourseIds; // Danh sách các ID của môn học tiên quyết
}
