package com.microservice.scheduleservice.dtos;

import com.microservice.scheduleservice.enums.DayOfWeek;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse{
    long id;
    long enrollmentId;
    DayOfWeek dayOfWeek;
    String lesson;
    String room;
}