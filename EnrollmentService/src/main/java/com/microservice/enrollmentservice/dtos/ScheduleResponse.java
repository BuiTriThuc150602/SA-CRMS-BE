package com.microservice.enrollmentservice.dtos;


import com.microservice.enrollmentservice.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
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