package com.microservice.classservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StatusEnrollClass {
    LOCKED(0),
    PLANNING(1);

    private int value;
}
