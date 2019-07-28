package com.GoEvent.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "movie_event")
public class MovieEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date date;


    @Temporal(TemporalType.TIME)
    private Date time;

    @Column
    private String price;

    @OneToOne(cascade = CascadeType.ALL)
    Theater theater;

    @OneToOne(cascade = CascadeType.ALL)
    Movie movie;


public String formatDateString(){
    DateFormat formatter = new SimpleDateFormat("MM/DD/yyyy");
    return formatter.format(date);
}

    public String formatTimeString(){
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        String str = formatter.format(time);
        System.out.println(str);
        return formatter.format(time);
    }
}
