package com.GoEvent.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Embeddable
public class Seat {


    @Column(name = "seats", columnDefinition = "BLOB")
    private int[] seats;


    public int getNumSeats() {
        return this.seats != null ? seats.length : 0;
    }

    public void setSeats(int length) {
        this.seats = new int[length];
        Arrays.fill(seats, 0);
    }

    public List<Integer> getFreeSeats() {
        List<Integer> seats = new ArrayList<>();
        setSeats(this.seats.length);
        for (int i = 0; i < this.seats.length; i++)
            if (this.seats[i] == 0)
                seats.add(i);
        return seats;
    }


    public int getSeats() {
        return this.seats.length;
    }


    public void saveSeats(int num) {
        this.seats[num] = 1;
    }

    public void cancelSeats(int num) {
        this.seats[num] = 0;
    }


}
