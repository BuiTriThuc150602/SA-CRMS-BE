package com.microservice.scheduleservice.models;

import com.microservice.scheduleservice.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id" )
    private long id;
    @Column(name = "enrollment_id" )
    private long enrollmentId;
    @Enumerated
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    //Tiết học
    @Column(name = "lesson")
    private String lesson;

    @Column(name = "room")
    private String room;


}
