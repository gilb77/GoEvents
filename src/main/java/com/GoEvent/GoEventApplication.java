package com.GoEvent;

import com.GoEvent.dao.movies.CinemaRepository;
import com.GoEvent.dao.movies.MovieEventRepository;
import com.GoEvent.dao.movies.MovieRepository;
import com.GoEvent.dao.movies.TheaterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;


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
//        Cinema cinema = new Cinema();
//        cinema.setName("tel aviv");
//        Theater theater = new Theater();
//        theater.setCinema(cinema);
//        theater.setSeats(new int[30]);
//        int a[] =  theater.getSeats();
//        a[0] = 20;
//        theater.setSeats( a);
//        theater.setName("number one");
//        cinema.getTheatersList().add(theater);
//        cinemaRepository.save(cinema);
//
//        Theater theaters = theaterRepository.findByName("number one");
//        int b[]=  theaters.getSeats();
//        System.out.println("Here = " + b[0]);

//        List<MovieEvent> movieEvents =  movieEventRepository.findAll();
//        List<MovieEvent> tempMovieEvents =  movieEventRepository.findAll();
//        int index =0;
//        List<Integer> list = new ArrayList<Integer>();
//
//        for(MovieEvent event: tempMovieEvents){
//            if(event.getMovie().getId() != 37) {
//                System.out.println("index = "+index);
//                movieEvents.remove((index));
//            }
//                index++;
//        }

//        for(Integer num: list){
//            movieEvents.remove(num);
//        }
    }




}


