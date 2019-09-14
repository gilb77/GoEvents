package com.GoEvent.model.liveshows;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Entity
@Getter
@Setter
@Table(name = "live_shows")
public class LiveShow {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    public int places;
    @Column
    public double costStanding;

    @Column(name = "seats", columnDefinition="BLOB")
    public int[] seats;
    @Column
    public double costSeating;

    @Column
    private String Address;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Temporal(TemporalType.TIME)
    private Date time;

    @OneToOne(cascade = CascadeType.ALL)
    private Artist artist;

    public int getNumSeats(){
        return seats.length;
    }

}
