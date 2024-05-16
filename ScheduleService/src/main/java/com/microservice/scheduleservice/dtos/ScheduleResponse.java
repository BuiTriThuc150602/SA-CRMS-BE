package com.microservice.scheduleservice.dtos;

import com.microservice.scheduleservice.enums.DayOfWeek;
import com.microservice.scheduleservice.enums.TypeEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse{
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