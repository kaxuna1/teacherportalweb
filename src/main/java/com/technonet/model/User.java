package com.technonet.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    private long id;
    @NotNull
    @Column
    private String username;
    @Column
    @NotNull
    @JsonIgnore
    private String password;
    @Column
    @NotNull
    private String email;
    @Column
    @NotNull
    private String name;
    @Column
    @NotNull
    private String surname;
    @Column
    private String address;
    @Column
    private String mobile;
    @Column
    @NotNull
    private String personalNumber;
    @Column
    private String profilePic;
    @Column
    @NotNull
    private int type;
    @Column
    private int sex;
    @Column
    private Date birthDate;
    @Column
    private boolean confirmedEmail;
    @Column
    private boolean confirmedSMS;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Session> sessions;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserCategoryJoin> userCategoryJoins;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "USER_PERMISSION")
    private List<Permission> permissions;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConfirmationToken> confirmationTokens;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GalleryPicture> galleryPictures;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookedTime> bookedTimesStudent;


    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;


    public User(long id) {
        this.id = id;
    }

    public User() {
    }


    public User(String username,
                String password,
                String email,
                String name,
                String surname,
                String address,
                String mobile,
                String personalNumber,
                int type,
                City city) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.mobile = mobile;
        this.personalNumber = personalNumber;
        this.type = type;
        this.sessions = new ArrayList<>();
        this.permissions = new ArrayList<>();
        this.city = city;
        this.documents = new ArrayList<>();
        this.userCategoryJoins = new ArrayList<>();
        this.galleryPictures = new ArrayList<>();
        this.confirmedEmail = false;
        this.confirmedSMS = false;
        this.confirmationTokens = new ArrayList<>();
        this.bookedTimesStudent=new ArrayList<>();
        this.orders=new ArrayList<>();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }


    public String getNameSurname() {
        return this.name + " " + this.surname;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<UserCategoryJoin> getUserCategoryJoins() {
        return userCategoryJoins;
    }

    public void setUserCategoryJoins(List<UserCategoryJoin> userCategoryJoins) {
        this.userCategoryJoins = userCategoryJoins;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<GalleryPicture> getGalleryPictures() {
        return galleryPictures;
    }

    public void setGalleryPictures(List<GalleryPicture> galleryPictures) {
        this.galleryPictures = galleryPictures;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isConfirmedEmail() {
        return confirmedEmail;
    }

    public void setConfirmedEmail(boolean confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
    }

    public boolean isConfirmedSMS() {
        return confirmedSMS;
    }

    public void setConfirmedSMS(boolean confirmedSMS) {
        this.confirmedSMS = confirmedSMS;
    }

    public List<ConfirmationToken> getConfirmationTokens() {
        return confirmationTokens;
    }

    public void setConfirmationTokens(List<ConfirmationToken> confirmationTokens) {
        this.confirmationTokens = confirmationTokens;
    }

    public List<BookedTime> getBookedTimesStudent() {
        return bookedTimesStudent;
    }

    public void setBookedTimesStudent(List<BookedTime> bookedTimesStudent) {
        this.bookedTimesStudent = bookedTimesStudent;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
