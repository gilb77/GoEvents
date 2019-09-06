package com.GoEvent.model;

import com.GoEvent.util.ParseUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "movie_event")
@AllArgsConstructor
@NoArgsConstructor
public class MovieEvent {


//    public MovieEvent(int movie, String city, int cinema, Date date, Date time){
//        this.date = date;
//       this.time=time;
//       this.movie = movie
//        movieEvent.setTheater(theaterService.getTheaterById(Integer.parseInt(json.get("theater"))));
//        movieEvent.setPrice(json.get("price"));
//    }

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
        return formatter.format(time);
    }

}
