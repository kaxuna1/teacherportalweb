package com.technonet.model;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by kakha on 3/11/2017.
 */
@Entity
@Table(name = "ScheduleTime")
public class ScheduleTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "scheduleTimeId")
    private long id;
    @Column
    private Time startTime;
    @Column
    private Time endTime;
    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;
    @Column
    private boolean active;

    public ScheduleTime(Time startTime, Time endTime, Schedule schedule) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.schedule = schedule;
        this.active = true;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
