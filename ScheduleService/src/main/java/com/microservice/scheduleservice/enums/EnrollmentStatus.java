package com.microservice.scheduleservice.enums;

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
