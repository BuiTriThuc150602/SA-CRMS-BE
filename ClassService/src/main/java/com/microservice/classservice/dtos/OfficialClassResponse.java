package com.microservice.classservice.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficialClassResponse {
    long id;
    String className;
    long instructorId;
    LocalDate registrationDate;
}