package com.technonet.model;

import javax.persistence.*;

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
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    @Column
    private boolean active;
    @Column
    private int dayOfWeek;

}
