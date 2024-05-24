package com.microservice.courseservice.controllers;

import com.microservice.courseservice.dtos.CourseRequest;
import com.microservice.courseservice.dtos.CourseResponse;
import com.microservice.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@RequestBody CourseRequest courseRequest){
        courseService.addCourse(courseRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getAllCourses(){
        return courseService.getAllCourse();
    }

    @GetMapping("/by-courseId")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse getCoursesById(@RequestParam String courseId){
        return courseService.getCoursesById(courseId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getCoursesBySemesterAndFaculty(
            @RequestParam String semester,
            @RequestParam String faculty) {
        return courseService.getCoursesBySemesterAndFaculty(semester, faculty);
    }
}
