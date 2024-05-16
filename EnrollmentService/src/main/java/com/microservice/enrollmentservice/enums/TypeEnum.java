package com.microservice.enrollmentservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TypeEnum {
    LT(0),
    TH(1);

    private int valuue;
}
