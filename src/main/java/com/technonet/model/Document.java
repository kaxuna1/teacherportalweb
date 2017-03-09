package com.technonet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kakha on 3/5/2017.
 */
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "documentId")
    private long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "countryId")
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "joinId")
    @JsonIgnore
    private UserCategoryJoin userCategoryJoin;
    @Column
    private String fileName;
    @Column
    private String extension;
    @Column
    private Date date;

    public Document(String name, User user, String fileName, String extension,UserCategoryJoin userCategoryJoin) {
        this.name = name;
        this.user = user;
        this.fileName = fileName;
        this.extension = extension;
        this.date=new Date();
        this.userCategoryJoin=userCategoryJoin;
    }
    public Document(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserCategoryJoin getUserCategoryJoin() {
        return userCategoryJoin;
    }

    public void setUserCategoryJoin(UserCategoryJoin userCategoryJoin) {
        this.userCategoryJoin = userCategoryJoin;
    }
}
