package com.GoEvent.model.liveshows;


import com.GoEvent.util.ParseUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Column(name = "seats", columnDefinition = "BLOB")
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

    public int getNumSeats() {
        return seats.length;
    }



    public void setSeats(int length){
        this.seats = new int[ length];
        Arrays.fill(seats,0);
    }


    public Iterable<Integer> getFreeSeats(){
        List<Integer> seats = new ArrayList<>();
        setSeats(this.seats.length);
        for (int i=0;i<this.seats.length;i++)
            if(this.seats[i]==0)
                seats.add(i);
        return seats;
    }
}
