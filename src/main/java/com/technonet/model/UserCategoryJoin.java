package com.technonet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
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

    @JsonIgnore
    @OneToMany(mappedBy = "userCategoryJoin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;


    public UserCategoryJoin(User user, Category categoryId) {
        this.user = user;
        this.category = categoryId;
        this.accepted = false;
        this.documents = new ArrayList<>();
    }
    public UserCategoryJoin(){}

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
}
