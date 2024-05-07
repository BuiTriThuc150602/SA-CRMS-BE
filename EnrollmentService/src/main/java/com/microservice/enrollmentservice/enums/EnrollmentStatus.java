package com.microservice.enrollmentservice.enums;

public enum EnrollmentStatus {
    REGISTER(0),
    CANCEL(1),
    PENDING(2);

    private int value;

    public int getValue() {
        return value;
    }

    EnrollmentStatus(int value) {
        this.value = value;
    }
}
