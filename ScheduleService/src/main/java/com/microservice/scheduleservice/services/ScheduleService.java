package com.microservice.scheduleservice.services;

import com.microservice.scheduleservice.dtos.*;
import com.microservice.scheduleservice.enums.DayOfWeek;
import com.microservice.scheduleservice.models.Schedule;
import com.microservice.scheduleservice.reponsitories.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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
            scheduleResponse.setId(schedule.getId());
            scheduleResponse.setEnrollmentClassId(schedule.getEnrollmentClassId());
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

    public ApiResponse<List<ScheduleResponse>> getListScheduleByEnrollmentClass(String enrollmentClassId) {
        EnrollmentClassResponse enrollmentClassResponse = getEnrollmentClassById(enrollmentClassId);
        if (enrollmentClassResponse.getCurrentStudents() < enrollmentClassResponse.getMaxStudent()) {
            List<Schedule> scheduleList = scheduleRepository.findScheduleByEnrollmentClassId(enrollmentClassId);
            List<ScheduleResponse> scheduleResponses = scheduleList.stream().map(schedule -> {
                ScheduleResponse scheduleResponse = new ScheduleResponse();
                scheduleResponse.setId(schedule.getId());
                scheduleResponse.setEnrollmentClassId(schedule.getEnrollmentClassId());
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
            return new ApiResponse<>(true, scheduleResponses, null);
        } else {
            return new ApiResponse<>(false, null, "The number of current students has reached the maximum limit.");
        }
    }


    public ScheduleResponse getScheduleById(String scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).get();
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setId(schedule.getId());
        scheduleResponse.setEnrollmentClassId(schedule.getEnrollmentClassId());
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

    public List<ScheduleDetailResponse> getListScheduleByEnrollment(String studentId, String semester) {
        List<EnrollmentResponse> enrollmentResponses = getEnrollmentsByStudentIdAndSemester(studentId, semester);
        List<ScheduleDetailResponse> scheduleResponseList = new ArrayList<>();
        if (!enrollmentResponses.isEmpty()) {
            for (EnrollmentResponse enrollmentResponse : enrollmentResponses) {
                ScheduleResponse scheduleResponse = getScheduleById(enrollmentResponse.getScheduleId());
                EnrollmentClassResponse enrollmentClassResponse = getEnrollmentClassById(enrollmentResponse.getClassId());

                ScheduleDetailResponse scheduleDetailResponse = new ScheduleDetailResponse();
                scheduleDetailResponse.setId(scheduleResponse.getId());
                scheduleDetailResponse.setEnrollmentClassId(enrollmentClassResponse.getId());
                scheduleDetailResponse.setClassName(enrollmentClassResponse.getClassName());
                scheduleDetailResponse.setCourseName(enrollmentClassResponse.getCourseName());
                scheduleDetailResponse.setDayOfWeek(scheduleResponse.getDayOfWeek());
                scheduleDetailResponse.setLesson(scheduleResponse.getLesson());
                scheduleDetailResponse.setPracticeGroup(scheduleResponse.getPracticeGroup());
                scheduleDetailResponse.setRoom(scheduleResponse.getRoom());
                scheduleDetailResponse.setBuilding(scheduleResponse.getBuilding());
                scheduleDetailResponse.setFacility(scheduleResponse.getFacility());
                scheduleDetailResponse.setTeacherId(scheduleResponse.getTeacherId());
                scheduleDetailResponse.setStartDate(scheduleResponse.getStartDate());
                scheduleDetailResponse.setFinishDate(scheduleResponse.getFinishDate());
                scheduleDetailResponse.setTypeEnum(scheduleResponse.getTypeEnum());
                scheduleResponseList.add(scheduleDetailResponse);
            }
        }
        return scheduleResponseList;
    }


    private EnrollmentClassResponse getEnrollmentClassById(String enrollmentClassId) {
        String baseUrl = "http://CLASSSERVICE/class/enrollmentclass/by-id";

        // Sử dụng UriComponentsBuilder để xây dựng URI với tham số yêu cầu
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("enrollmentClassId", enrollmentClassId)
                .toUriString();

        return loadBalancedWebClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(EnrollmentClassResponse.class)
                .block();
    }

    public boolean checkDuplicatedSchedule(String studentId, String semester, String scheduleId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (!optionalSchedule.isPresent()) {
            return false;
        }
        Schedule schedule = optionalSchedule.get();
        DayOfWeek dayOfWeek = schedule.getDayOfWeek();
        String lesson = schedule.getLesson();
        List<EnrollmentResponse> enrollmentResponses = getEnrollmentsByStudentIdAndSemester(studentId, semester);

        for (EnrollmentResponse enrollmentResponse : enrollmentResponses) {
            String scheduleIdOfEnrollment = enrollmentResponse.getScheduleId();
            log.info("scheduleIdOfEnrollment :" + scheduleIdOfEnrollment);
            if (scheduleIdOfEnrollment != null) {
                Optional<Schedule> optionalScheduleOfEnrollment = scheduleRepository.findById(scheduleIdOfEnrollment);

                if (optionalScheduleOfEnrollment.isPresent()) {
                    Schedule scheduleOfEnrollment = optionalScheduleOfEnrollment.get();
                    if (scheduleOfEnrollment.getDayOfWeek().equals(dayOfWeek) &&
                            scheduleOfEnrollment.getLesson().equals(lesson)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public List<EnrollmentResponse> getEnrollmentsByStudentIdAndSemester(String studentId, String semester) {
        String baseUrl = "http://ENROLLMENTSERVICE/enrollment/search";
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("studentId", studentId)
                .queryParam("semester", semester)
                .toUriString();

        EnrollmentResponse[] enrollmentResponses = loadBalancedWebClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(EnrollmentResponse[].class)
                .block();

        log.info("enrollmentResponses:" + Arrays.toString(enrollmentResponses));  // Log array

        if (enrollmentResponses != null) {
            List<EnrollmentResponse> enrollmentResponseList = Arrays.stream(enrollmentResponses)
                    .collect(Collectors.toList());
            log.info("enrollmentResponseList:" + enrollmentResponseList);  // Log danh sách chuyển đổi từ array
            return enrollmentResponseList;
        } else {
            return List.of();  // Trả về danh sách rỗng nếu phản hồi là null
        }
    }

}
