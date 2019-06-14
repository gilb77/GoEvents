package com.GoEvent;

import com.GoEvent.dao.CinemaRepository;
import com.GoEvent.dao.TheaterRepository;
import com.GoEvent.model.Cinema;
import com.GoEvent.model.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

import java.util.ArrayList;
//@EnableAutoConfiguration(exclude=ErrorMvcAutoConfiguration.class)
@SpringBootApplication
public class GoEventApplication implements CommandLineRunner {

    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private TheaterRepository theaterRepository;

    public static void main(String[] args) {
        SpringApplication.run(GoEventApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Cinema cinema = new Cinema();
        cinema.setName("tel aviv");
        cinema.setTheatersList(new ArrayList<>());
        Theater theater = new Theater();
        theater.setCinema(cinema);
        theater.setSeats(100);
        theater.setName("number one");
        cinema.getTheatersList().add(theater);
        cinemaRepository.save(cinema);
        Theater theaters = theaterRepository.findByName("number one");
        System.out.println("Here = " + theater.getName());
    }


}


