package com.microservice.scheduleservice.reponsitories;

import com.microservice.scheduleservice.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select S from Schedule S where S.enrollmentClassId =?1")
    List<Schedule> findScheduleByEnrollmentClassId(String enrollmentClassId);

    @Query("select S from Schedule S where S.id =?1")
    Optional<Schedule> findById(String scheduleId);
}