package com.GoEvent.service.impl;


import com.GoEvent.dao.MovieEventRepository;
import com.GoEvent.model.MovieEvent;
import com.GoEvent.service.EventService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.internal.thread.ThreadExecutionException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Service
public class MovieEventServiceImpl implements EventService {

    private MovieEventRepository movieEventRepository;


    @Autowired
    public void setMovieEventRepository(MovieEventRepository movieEventRepository) {
        this.movieEventRepository = movieEventRepository;
    }

    public void saveEvent(MovieEvent movieEvent) {
        this.movieEventRepository.save(movieEvent);
    }


    public Iterable<MovieEvent> listAllEvents() {
        return movieEventRepository.findAll();
    }


    public Iterable<MovieEvent> listAllEventsByMovie(int movie) {
        Iterable<MovieEvent> movieEvents = movieEventRepository.findAll();
        Iterable<MovieEvent> tempMovieEvents = movieEventRepository.findAll();
        int index = 0;
        for (MovieEvent event : tempMovieEvents) {
            if (event.getMovie().getId() != movie)
                ((List<MovieEvent>) movieEvents).remove(index);
            index++;
        }
        return movieEvents;
    }


    public Iterable<MovieEvent> listAllEventByCity(int movie, String city) {
        Iterable<MovieEvent> movieEvents = movieEventRepository.findAll();
        Iterable<MovieEvent> tempMovieEvents = movieEvents;
        int index = 0;
        for (MovieEvent event : tempMovieEvents) {
            if (!StringUtils.equalsIgnoreCase(
                    event.getTheater().getCinema().getCity(),
                    city) &&
                    event.getMovie().getId() != movie)
                ((List<MovieEvent>) movieEvents).remove(index);
            index++;
        }
        return movieEvents;
    }


    public Iterable<MovieEvent> listAllEventByCinema(int movie, String city, int cinema) {
        Iterable<MovieEvent> movieEvents = movieEventRepository.findAll();
        Iterable<MovieEvent> tempMovieEvents = movieEvents;
        int index = 0;
        for (MovieEvent event : tempMovieEvents) {
            if (!StringUtils.equalsIgnoreCase(
                    event.getTheater().getCinema().getCity(),
                    city) &&
                    event.getMovie().getId() != movie &&
                    event.getTheater().getCinema().getId() != cinema)
                ((List<MovieEvent>) movieEvents).remove(index);
            index++;
        }
        return movieEvents;
    }


    public Iterable<MovieEvent> listAllEventByDate(int movie, String city, int cinema, Date date) throws ParseException {
        Iterable<MovieEvent> movieEvents = movieEventRepository.findAll();
        Iterable<MovieEvent> tempMovieEvents = movieEvents;
        int index = 0;
        for (MovieEvent event : tempMovieEvents) {
            //check if the event is different from the argument we get in the method,
            // in case of true we remove him from the list.
            if (!StringUtils.equalsIgnoreCase(
                    event.getTheater().getCinema().getCity(),
                    city) &&//check the name city
                    event.getMovie().getId() != movie && //check movie id
                    event.getTheater().getCinema().getId() != cinema &&// check cinema id
                    !event.getDate().equals(date))
                ((List<MovieEvent>) movieEvents).remove(index);
            index++;
        }
        return movieEvents;
    }

    public Iterable<Integer> listAllSeatsByTime(int movie, String city, int cinema, Date date, Date time) throws Exception {
       return getMovieEvent( movie,city,cinema,date,time).getTheater().getFreeSeats();
    }




    public MovieEvent getMovieEvent(int movie, String city, int cinema, Date date, Date time) throws Exception {
        Iterable<MovieEvent> movieEvents = movieEventRepository.findAll();
        for (MovieEvent event : movieEvents) {
            if (StringUtils.equalsIgnoreCase(
                    event.getTheater().getCinema().getCity(), city) &&
                    event.getMovie().getId() == movie &&
                    event.getTheater().getCinema().getId() == cinema &&
                    event.getDate().equals(date) &&
                    event.getTime().compareTo(time) == 0)
                return event;
        }
        throw new Exception();
    }


}
