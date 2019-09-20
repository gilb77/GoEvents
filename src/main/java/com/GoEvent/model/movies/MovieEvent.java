package com.GoEvent.model.movies;

import com.GoEvent.model.Event;
import lombok.*;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie_event")
public class MovieEvent extends Event {

    @OneToOne(cascade = CascadeType.ALL)
    Theater theater;

    @OneToOne(cascade = CascadeType.ALL)
    Movie movie;

}
