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

    private String name;
    @Column
    private int seats;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theaters_id")
    private Cinema cinema;



}
