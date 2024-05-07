package com.microservice.scheduleservice.services;

import com.microservice.scheduleservice.dtos.ScheduleRequest;
import com.microservice.scheduleservice.dtos.ScheduleResponse;
import com.microservice.scheduleservice.models.Schedule;
import com.microservice.scheduleservice.reponsitories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public void createSchedule(ScheduleRequest scheduleRequest) {
        Schedule schedule = new Schedule();
        schedule.setEnrollmentId(scheduleRequest.getEnrollmentId());
        schedule.setDayOfWeek(scheduleRequest.getDayOfWeek());
        schedule.setLesson(scheduleRequest.getLesson());
        schedule.setRoom(scheduleRequest.getRoom());

        scheduleRepository.save(schedule);
    }

    public List<ScheduleResponse> getAll() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleResponse> scheduleResponses = scheduleList.stream().map(schedule -> {
            ScheduleResponse scheduleResponse = new ScheduleResponse();
            scheduleResponse.setId(schedule.getId());
            scheduleResponse.setEnrollmentId(schedule.getEnrollmentId());
            scheduleResponse.setDayOfWeek(schedule.getDayOfWeek());
            scheduleResponse.setLesson(schedule.getLesson());
            scheduleResponse.setRoom(schedule.getRoom());
            return scheduleResponse;
        }).collect(Collectors.toList());
        return scheduleResponses;

    }

}
