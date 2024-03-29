package com.GoEvent.model.liveshows;


import com.GoEvent.model.Event;
import com.GoEvent.model.Seat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "live_shows")
public class LiveShow extends Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    public int stand;

    @Column
    public double costSeating;

    @Column
    public double costStanding;

    @OneToOne()
    @JoinColumn(name = "location_id", nullable = false)
    private Location Location;

    @OneToOne()
    private Artist artist;


     public boolean  saveStandPlace(int num) {
         if (stand<num)
             return false;
         this.stand-=num;
         return true;
    }
}
