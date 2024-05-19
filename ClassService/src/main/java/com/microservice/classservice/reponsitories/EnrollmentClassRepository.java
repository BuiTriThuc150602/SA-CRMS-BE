package com.microservice.classservice.reponsitories;

import com.microservice.classservice.models.EnrollmentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EnrollmentClassRepository extends JpaRepository<EnrollmentClass, String> {
    @Query("SELECT EC FROM EnrollmentClass EC WHERE EC.courseId =?1")
    List<EnrollmentClass> findByCoureId(String courseId);
    @Query("SELECT EC FROM EnrollmentClass EC WHERE EC.id =?1")
    Optional<EnrollmentClass> findById(String enrollmentClassId);


    @Modifying
    @Query("UPDATE EnrollmentClass EC SET EC.currentStudents=EC.currentStudents+1 WHERE EC.id = ?1")
    void incrementCurrentStudents(String enrollmentClassId);

}