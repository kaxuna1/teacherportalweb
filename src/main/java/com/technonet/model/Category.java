package com.technonet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by kakha on 3/7/2017.
 */
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryId")
    private long id;

    @Column
    private String name;

    @Column
    private String uuid;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "CATEGORY_DOCTYPE")
    private List<DocType> docTypes;


    public Category(String name) {
        this.name = name;
        uuid= UUID.randomUUID().toString();
        docTypes=new ArrayList<>();
    }
    public Category(){}

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<DocType> getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(List<DocType> docTypes) {
        this.docTypes = docTypes;
    }
}
