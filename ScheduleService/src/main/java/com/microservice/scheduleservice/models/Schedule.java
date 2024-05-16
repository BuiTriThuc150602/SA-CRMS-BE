package com.microservice.scheduleservice.models;

import com.microservice.scheduleservice.enums.DayOfWeek;
import com.microservice.scheduleservice.enums.TypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @Column(name = "schedule_id" )
    private String id;
    @Column(name = "class_id" )
    private String enrollmentClassId;
    @Enumerated
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    //Tiết học
    @Column(name = "lesson")
    private String lesson;
    @Column(name = "practice_group")
    private String practiceGroup;
    @Column(name = "room")
    private String room;
    private String building;
    private String facility;
    private String teacherId;
    private LocalDate startDate;
    private LocalDate finishDate;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private TypeEnum typeEnum;
}
