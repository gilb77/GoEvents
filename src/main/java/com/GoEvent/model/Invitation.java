package com.GoEvent.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "INVITATION")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "eventId")
    private int eventId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "seats", columnDefinition = "BLOB")
    private int[] seats;

    @Column(name = "standsCount")
    private int standsCount;

    @Column(name = "totalCost")
    private double totalCost;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public void setSeats(int length) {
        this.seats = new int[length];
    }

}
