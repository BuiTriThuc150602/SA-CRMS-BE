package com.microservice.courseservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CourseResponse {
    private long id;
    private String name;
    private int credit;
    private List<Long> prerequisiteCourseIds; // Danh sách các ID của môn học tiên quyết
}
