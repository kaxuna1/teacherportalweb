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

    @JsonIgnore
    @OneToMany(mappedBy = "userCategoryJoin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;

    @Column
    private Date date;

    @Column
    private Date lastModifyDate;


    public UserCategoryJoin(User user, Category categoryId) {
        this.user = user;
        this.category = categoryId;
        this.accepted = false;
        this.documents = new ArrayList<>();
        this.declined = false;
        this.date = new Date();
        this.lastModifyDate = new Date();
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
}
