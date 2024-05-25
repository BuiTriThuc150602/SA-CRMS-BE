package com.microservice.courseservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CollectionStatus {
    COLLECTED(0),
    NOT_YET_COLLECTED(1);

    private int value;
}
