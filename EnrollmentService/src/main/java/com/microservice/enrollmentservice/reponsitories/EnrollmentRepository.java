package com.microservice.enrollmentservice.reponsitories;

import com.microservice.enrollmentservice.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("select e from Enrollment e where e.studentId =?1 and e.semester =?2")
    List<Enrollment> findEnrollmentsByStudentIdAndSemester(String studentId, String semester);
}