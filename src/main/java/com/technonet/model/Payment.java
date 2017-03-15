package com.technonet.model;

import javax.persistence.*;
import java.util.Date;

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
    private boolean confirmed;

    @Column
    private String transaction;





}
