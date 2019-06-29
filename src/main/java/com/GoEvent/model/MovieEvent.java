package com.GoEvent.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "movie_event")
public class MovieEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String date;

    @OneToOne(cascade = CascadeType.ALL)
    Theater theater;


    @OneToOne(cascade = CascadeType.ALL)
    Movie movie;

}
