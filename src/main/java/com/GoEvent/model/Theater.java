package com.GoEvent.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "theaters")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "seats", columnDefinition="BLOB")
    private int[] seats;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;


    public int getSeats(){
        return this.seats.length;
    }


    public void setSeats(int length){
        this.seats = new int[ length];
        Arrays.fill(seats,0);
    }

    public void saveSeats(int num) {
        this.seats[num] = 1;
    }

    public void cancelSeats(int num) {
        this.seats[num] = 0;
    }

    public Iterable<Integer> getFreeSeats(){
        List<Integer> seats = new ArrayList<>();
        for (int i=0;i<this.seats.length;i++)
            if(this.seats[i]==0)
                seats.add(i);
        return seats;
     }
}
