package com.microservice.scheduleservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DayOfWeek {
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    private int vaule;
}
