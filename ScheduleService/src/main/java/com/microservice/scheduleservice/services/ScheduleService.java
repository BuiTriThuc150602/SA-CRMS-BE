package com.microservice.scheduleservice.services;

import com.microservice.scheduleservice.dtos.EnrollmentClassResponse;
import com.microservice.scheduleservice.dtos.ScheduleRequest;
import com.microservice.scheduleservice.dtos.ScheduleResponse;
import com.microservice.scheduleservice.exceptions.MaxStudentsReachedException;
import com.microservice.scheduleservice.models.Schedule;
import com.microservice.scheduleservice.reponsitories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private WebClient.Builder loadBalancedWebClientBuilder;

    public void createSchedule(ScheduleRequest scheduleRequest) {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleRequest.getId());
        schedule.setEnrollmentClassId(scheduleRequest.getEnrollmentClassId());
        schedule.setDayOfWeek(scheduleRequest.getDayOfWeek());
        schedule.setLesson(scheduleRequest.getLesson());
        schedule.setPracticeGroup(scheduleRequest.getPracticeGroup());
        schedule.setRoom(scheduleRequest.getRoom());
        schedule.setBuilding(scheduleRequest.getBuilding());
        schedule.setFacility(scheduleRequest.getFacility());
        schedule.setTeacherId(scheduleRequest.getTeacherId());
        schedule.setStartDate(scheduleRequest.getStartDate());
        schedule.setFinishDate(scheduleRequest.getFinishDate());
        schedule.setTypeEnum(scheduleRequest.getTypeEnum());
        scheduleRepository.save(schedule);
    }

    public List<ScheduleResponse> getAll() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleResponse> scheduleResponses = scheduleList.stream().map(schedule -> {
            ScheduleResponse scheduleResponse = new ScheduleResponse();
            scheduleResponse.setDayOfWeek(schedule.getDayOfWeek());
            scheduleResponse.setLesson(schedule.getLesson());
            scheduleResponse.setPracticeGroup(schedule.getPracticeGroup());
            scheduleResponse.setRoom(schedule.getRoom());
            scheduleResponse.setBuilding(schedule.getBuilding());
            scheduleResponse.setFacility(schedule.getFacility());
            scheduleResponse.setTeacherId(schedule.getTeacherId());
            scheduleResponse.setStartDate(schedule.getStartDate());
            scheduleResponse.setFinishDate(schedule.getFinishDate());
            scheduleResponse.setTypeEnum(schedule.getTypeEnum());
            return scheduleResponse;
        }).collect(Collectors.toList());
        return scheduleResponses;
    }

    public List<ScheduleResponse> getListScheduleByEnrollmentClass(String enrollmentClassId) {
        EnrollmentClassResponse enrollmentClassResponse = getEnrollmentClassById(enrollmentClassId);
        if (enrollmentClassResponse.getCurrentStudents() < enrollmentClassResponse.getMaxStudent()) {
            List<Schedule> scheduleList = scheduleRepository.findScheduleByEnrollmentClassId(enrollmentClassId);
            List<ScheduleResponse> scheduleResponses = scheduleList.stream().map(schedule -> {
                ScheduleResponse scheduleResponse = new ScheduleResponse();
                scheduleResponse.setDayOfWeek(schedule.getDayOfWeek());
                scheduleResponse.setLesson(schedule.getLesson());
                scheduleResponse.setPracticeGroup(schedule.getPracticeGroup());
                scheduleResponse.setRoom(schedule.getRoom());
                scheduleResponse.setBuilding(schedule.getBuilding());
                scheduleResponse.setFacility(schedule.getFacility());
                scheduleResponse.setTeacherId(schedule.getTeacherId());
                scheduleResponse.setStartDate(schedule.getStartDate());
                scheduleResponse.setFinishDate(schedule.getFinishDate());
                scheduleResponse.setTypeEnum(schedule.getTypeEnum());
                return scheduleResponse;
            }).collect(Collectors.toList());
            return scheduleResponses;
        }else{
            throw new MaxStudentsReachedException("The number of current students has reached the maximum limit.");
        }
    }


    public ScheduleResponse getScheduleById(String scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).get();
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setDayOfWeek(schedule.getDayOfWeek());
        scheduleResponse.setLesson(schedule.getLesson());
        scheduleResponse.setPracticeGroup(schedule.getPracticeGroup());
        scheduleResponse.setRoom(schedule.getRoom());
        scheduleResponse.setBuilding(schedule.getBuilding());
        scheduleResponse.setFacility(schedule.getFacility());
        scheduleResponse.setTeacherId(schedule.getTeacherId());
        scheduleResponse.setStartDate(schedule.getStartDate());
        scheduleResponse.setFinishDate(schedule.getFinishDate());
        scheduleResponse.setTypeEnum(schedule.getTypeEnum());
        return scheduleResponse;
    }


    private EnrollmentClassResponse getEnrollmentClassById(String enrollmentClassId) {
        return loadBalancedWebClientBuilder.build()
                .get()
                .uri("http://CLASSSERVICE/class/enrollmentclass/by-id/{enrollmentClassId}", enrollmentClassId)
                .retrieve()
                .bodyToMono(EnrollmentClassResponse.class)
                .block();
    }


}
