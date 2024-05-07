package com.microservice.classservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "official_class")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficialClass extends  Class{
    @Column(name = "registration_date")
    private LocalDate registrationDate;
}
