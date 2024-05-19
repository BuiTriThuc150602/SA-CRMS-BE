package com.microservice.scheduleservice.dtos;

import com.microservice.scheduleservice.enums.DayOfWeek;
import com.microservice.scheduleservice.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDetailResponse {
    private String id;
    private String enrollmentClassId;
    private String className;
    private String courseName;
    private DayOfWeek dayOfWeek;
    private String lesson;
    private String practiceGroup;
    private String room;
    private String building;
    private String facility;
    private String teacherId;
    private LocalDate startDate;
    private LocalDate finishDate;
    private TypeEnum typeEnum;
}
