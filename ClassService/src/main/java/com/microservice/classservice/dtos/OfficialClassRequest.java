package com.microservice.classservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficialClassRequest  {
    String className;
    long instructorId;
    LocalDate registrationDate;
}