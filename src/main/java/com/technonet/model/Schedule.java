package com.technonet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.technonet.staticData.Variables;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaxa on 3/11/17.
 */
@Entity
@Table(name = "Schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "scheduleId")
    private long id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    @Column
    private boolean active;
    @Column
    private int dayOfWeek;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ScheduleTime> scheduleTimes;

    public Schedule(User user, Category category, int dayOfWeek) {
        this.user = user;
        this.category = category;
        this.dayOfWeek = dayOfWeek;
        this.active=true;
        this.scheduleTimes=new ArrayList<>();
    }

    public Schedule(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<ScheduleTime> getScheduleTimes() {
        return scheduleTimes;
    }

    public void setScheduleTimes(List<ScheduleTime> scheduleTimes) {
        this.scheduleTimes = scheduleTimes;
    }

    public String getName(){
        return Variables.getWeekDays().get(dayOfWeek).name;
    }
    public int getWorkHours(){
        return 2;
    }
}
