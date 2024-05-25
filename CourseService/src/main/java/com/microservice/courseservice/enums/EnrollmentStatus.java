package com.microservice.courseservice.enums;

public enum EnrollmentStatus {
    NEW(0),
    OLD(1);

    private int value;

    public int getValue() {
        return value;
    }

    EnrollmentStatus(int value) {
        this.value = value;
    }
}
