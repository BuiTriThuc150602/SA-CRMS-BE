package com.microservice.learningresultservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ClassificationEnum {
    EXCELLENT(0),
    DISTINCTION(1),
    GOOD(2),
    AVERAGE(3),
    POOR(4);

    private int value;
}
