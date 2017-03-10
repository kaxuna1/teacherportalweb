package com.technonet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.technonet.Enums.ConfirmationTypes;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by kaxa on 3/11/17.
 */
@Entity
@Table(name = "ConfirmationToken")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tokenId")
    private long id;

    @Column
    private String token;

    @Column
    private int type;

    @Column
    private Date date;

    @Column
    private boolean confimed;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    public ConfirmationToken(int type, User user) {
        this.type = type;
        this.user = user;
        if(type== ConfirmationTypes.EMAIL.getCODE()){
            this.token = UUID.randomUUID().toString();
        }else{
            this.token = ((new Random().nextInt(9000) + 1000)+"");
        }
        this.date = new Date();
        this.confimed = false;
    }
    public ConfirmationToken(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isConfimed() {
        return confimed;
    }

    public void setConfimed(boolean confimed) {
        this.confimed = confimed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
