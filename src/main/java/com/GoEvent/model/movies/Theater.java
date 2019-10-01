package com.GoEvent.model.movies;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "THEATERS")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    int seats;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

}
