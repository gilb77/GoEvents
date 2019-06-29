package com.GoEvent;

import com.GoEvent.dao.CinemaRepository;
import com.GoEvent.dao.MovieEventRepository;
import com.GoEvent.dao.MovieRepository;
import com.GoEvent.dao.TheaterRepository;
import com.GoEvent.model.Cinema;
import com.GoEvent.model.Theater;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

import java.util.ArrayList;

@SpringBootApplication
public class GoEventApplication implements CommandLineRunner {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private MovieEventRepository movieEventRepository ;

    @Autowired
    private MovieRepository movieRepository;

    public static void main(String[] args) {
        SpringApplication.run(GoEventApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Cinema cinema = new Cinema();
        cinema.setName("tel aviv");
        Theater theater = new Theater();
        theater.setCinema(cinema);
        theater.setSeats(new int[30]);
        int a[] =  theater.getSeats();
        a[0] = 20;
        theater.setSeats( a);
        theater.setName("number one");
        cinema.getTheatersList().add(theater);
        cinemaRepository.save(cinema);

        Theater theaters = theaterRepository.findByName("number one");
        int b[]=  theaters.getSeats();
        System.out.println("Here = " + b[0]);

    }


}


