package com.technonet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kaxa on 3/8/17.
 */
@Entity
@Table(name = "UserCategoryJoin")
public class UserCategoryJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "joinId")
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column
    private boolean accepted;

    @Column
    private boolean declined;

    @Column
    private boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "userCategoryJoin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;

    @JsonIgnore
    @OneToMany(mappedBy = "userCategoryJoin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookedTime> bookedTimes;

    @Column
    private Date date;

    @Column
    private Date lastModifyDate;

    @OneToMany(mappedBy = "userCategoryJoin", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Schedule> schedules;

    @Column
    private float price;

    @Column
    private int duration;


    public UserCategoryJoin(User user, Category categoryId, float price, int duration) {
        this.user = user;
        this.category = categoryId;
        this.duration = duration;
        this.accepted = false;
        this.documents = new ArrayList<>();
        this.declined = false;
        this.date = new Date();
        this.lastModifyDate = new Date();
        this.active=true;
        this.schedules=new ArrayList<>();
        this.price=price;
        this.bookedTimes =new ArrayList<>();
    }

    public UserCategoryJoin() {
    }

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

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public boolean isDeclined() {
        return declined;
    }

    public void setDeclined(boolean declined) {
        this.declined = declined;
    }

    public void confirm() {
        this.accepted = true;
        this.lastModifyDate = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public void decline() {
        this.declined = true;
        this.accepted = false;
        this.lastModifyDate = new Date();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<BookedTime> getBookedTimes() {
        return bookedTimes;
    }

    public void setBookedTimes(List<BookedTime> bookedTimes) {
        this.bookedTimes = bookedTimes;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
