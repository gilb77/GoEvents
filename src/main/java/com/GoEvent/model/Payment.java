package com.GoEvent.model;

import com.GoEvent.model.movies.MovieInvite;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private MovieInvite movieInvite;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

}
