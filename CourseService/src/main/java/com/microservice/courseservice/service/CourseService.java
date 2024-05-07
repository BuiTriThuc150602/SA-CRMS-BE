package com.microservice.courseservice.service;

import com.microservice.courseservice.dtos.CourseRequest;
import com.microservice.courseservice.dtos.CourseResponse;
import com.microservice.courseservice.models.Course;
import com.microservice.courseservice.reponsitories.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

    @Service
    @Slf4j
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public void addCourse(CourseRequest courseRequest) {
        Course course = new Course();
        course.setName(courseRequest.getName());
        course.setCredit(courseRequest.getCredit());
        course.setPrerequisiteIds(courseRequest.getPrerequisiteIds());

        courseRepository.save(course);
        log.info("Course with id " + course.getId() + " is saved");
    }

    public List<CourseResponse> getAllCourse() {
        List<Course> courses = courseRepository.findAll();

        List<CourseResponse> courseResponses = courses.stream().map(course -> {
            CourseResponse courseResponse = new CourseResponse();
            courseResponse.setId(course.getId());
            courseResponse.setName(course.getName());
            courseResponse.setCredit(course.getCredit());

            List<Long> prerequisiteCourseIds = course.getPrerequisiteIds();
            courseResponse.setPrerequisiteCourseIds(prerequisiteCourseIds);
            return courseResponse;
        }).collect(Collectors.toList());
        return courseResponses;
    }
}
