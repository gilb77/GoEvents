package com.GoEvent.model.movies;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "cinema")
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    public String name;

    @Column
    public String city;

    @Column
    public String address;


    @OneToMany(mappedBy = "cinema",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
     private List<Theater> theatersList =new ArrayList<>();
}
