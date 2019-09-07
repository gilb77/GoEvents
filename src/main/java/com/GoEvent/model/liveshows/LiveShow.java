package com.GoEvent.model.liveshows;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "live_show")
public class LiveShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int stands;

    @Column(name = "seats", columnDefinition="BLOB")
    private int[] seats;

    @Column
    private double cost;

    @Column
    private String city;

    @Column
    private Date date;

    @Column
    private Date time;

    @OneToOne(cascade = CascadeType.ALL)
    private Artist artist;
}
