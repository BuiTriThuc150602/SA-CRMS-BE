package com.microservice.scheduleservice.dtos;

import com.microservice.scheduleservice.enums.DayOfWeek;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {
    long enrollmentId;
    DayOfWeek dayOfWeek;
    String lesson;
    String room;
}