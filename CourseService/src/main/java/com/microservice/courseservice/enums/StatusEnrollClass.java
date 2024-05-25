package com.microservice.courseservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StatusEnrollClass {
    LOCKED(0),
    PLANNING(1);

    private int value;
}
