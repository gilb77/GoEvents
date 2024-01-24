package com.GoEvent.model;

import com.GoEvent.model.liveshows.LiveShow;
import com.GoEvent.model.movies.MovieEvent;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@MappedSuperclass
public class Event {

    public Event() {
        this.seat = new Seat();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Temporal(TemporalType.TIME)
    private Date time;

    @Embedded
    Seat seat;

    @Column
    public double price;

    public MovieEvent getMovieEvent() {
        if (this instanceof MovieEvent)
            return (MovieEvent) this;
        return null;
    }


    public LiveShow getLiveShow() {
        if (this instanceof LiveShow)
            return (LiveShow) this;
        return null;
    }


}
