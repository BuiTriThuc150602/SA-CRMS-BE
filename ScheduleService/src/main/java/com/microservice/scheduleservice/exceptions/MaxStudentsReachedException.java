package com.microservice.scheduleservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaxStudentsReachedException extends RuntimeException{
    public MaxStudentsReachedException(String message) {
        super(message);
    }
}
