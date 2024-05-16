package com.microservice.courseservice.reponsitories;

import com.microservice.courseservice.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, String> {
    @Query("select c from Course c where c.id =?1")
    Optional<Course> findCourseById(String courseId);

    @Query("select c from Course c where c.semester=?1 and c.faculty=?2")
    List<Course> findCoursesBySemesterAndFaculty(String semester, String faculty);
}