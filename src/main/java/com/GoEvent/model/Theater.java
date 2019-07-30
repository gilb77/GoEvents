package com.GoEvent.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;


    public int getSeats(){
        return this.seats.length;
    }


    public void setSeats(int length){
        this.seats = new int[ length];
    }


}
