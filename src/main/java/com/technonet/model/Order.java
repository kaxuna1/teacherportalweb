package com.technonet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;

/**
 * Created by kakha on 3/15/2017.
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderId")
    private long id;

    @Column
    private String uuid;

    @Column
    private Date createDate;

    @Column
    private Date lastModifyDate;

    @Column
    private Date confirmDate;

    @Column
    private boolean confirmed;

    @Column
    private boolean refunded;

    @Column
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookedTime> bookedTimes;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;

    @Column
    private float price;


    public Order(User user) {
        this.user = user;
        this.active = true;
        this.confirmed = false;
        this.refunded = false;
        this.bookedTimes = new ArrayList<>();
        this.createDate = new Date();
        this.lastModifyDate = new Date();
        this.uuid = UUID.randomUUID().toString();
        this.payments = new ArrayList<>();
    }

    public Order() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BookedTime> getBookedTimes() {
        return bookedTimes;
    }

    public void setBookedTimes(List<BookedTime> bookedTimes) {
        this.bookedTimes = bookedTimes;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    public float getOrderPrice(){
        return price;
    }
    public float getPayementsMade(){
        return (float)this.payments.stream()
                .filter(payment -> payment.isActive()&&payment
                        .isConfirmed()).mapToDouble(value ->
                        (double)value.getPrice()).sum();
    }

    public void confirm() {
        this.confirmDate=new Date();
        this.confirmed=true;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
