package com.technonet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.DoubleStream;

/**
 * Created by kakha on 3/15/2017.
 */
@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "paymentId")
    private long id;

    @Column
    private String uuid;

    @Column
    private float price;

    @Column
    private Date date;

    @Column
    private Date confirmDate;

    @Column
    private boolean confirmed;

    @Column
    private String transaction;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Order order;

    @Column
    private boolean active;


    public Payment(float price, Order order) {
        this.price = price;
        this.order = order;
        this.confirmed = false;
        this.active = true;
        this.uuid = UUID.randomUUID().toString();
        this.date = new Date();
    }

    public Payment(){

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public void confirm() {
        this.confirmed=true;
        this.confirmDate=new Date();
        if((this.price+this.order.getPayementsMade())>=order.getOrderPrice()){
            this.order.confirm();
        }
    }
}
