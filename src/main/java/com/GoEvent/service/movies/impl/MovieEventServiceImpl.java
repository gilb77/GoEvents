package com.GoEvent.service.movies.impl;


import com.GoEvent.dao.movies.MovieEventRepository;
import com.GoEvent.model.movies.MovieEvent;
import com.GoEvent.service.EventService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public List<MovieEvent> listAllEvents() {
        return movieEventRepository.findAll();
    }

    public List<MovieEvent> getMovieEventByFilter(int movie) {
        return getMovieEventByFilter(movie, null, -1, null, null);
    }

    public List<MovieEvent> getMovieEventByFilter(int movie, String city) {
        return getMovieEventByFilter(movie, city, -1, null, null);
    }

    public List<MovieEvent> getMovieEventByFilter(int movie, String city, int cinema) {
        return getMovieEventByFilter(movie, city, cinema, null, null);
    }

    public List<MovieEvent> getMovieEventByFilter(int movie, String city, int cinema, Date date) {
        return getMovieEventByFilter(movie, city, cinema, date, null);
    }


    public List<MovieEvent> getMovieEventByFilter(int movie, String city, int cinema, Date date, Date time) {
        List<MovieEvent> movieEvents = movieEventRepository.findAll();
        List<MovieEvent> tempMovieEvents = new ArrayList<>();
        for (MovieEvent event : movieEvents) {
            if (checkMovie(event, movie) && checkCity(event, city) &&
                    checkCinema(event, cinema) && checkDate(event, date) && checkTime(event, time))
                tempMovieEvents.add(event);
        }
        return tempMovieEvents;
    }

    public Iterable<Integer> listAllSeatsByTime(int movie, String city, int cinema, Date date, Date time) throws Exception {
        return getMovieEvent(movie, city, cinema, date, time).getTheater().getFreeSeats();
    }

    public MovieEvent getMovieEvent(int movie, String city, int cinema, Date date, Date time) throws Exception {
        List<MovieEvent> movieEvent = getMovieEventByFilter(movie, city, cinema, date, time);
        if (movieEvent.isEmpty())
            throw new Exception("The event not exists");
        if(movieEvent.size()>1)
            throw new Exception("The event is duplicate");
        return movieEvent.get(0);
    }

    private boolean checkMovie(MovieEvent event, int movie) {
        return movie == -1 || (event.getMovie().getId() == movie);
    }

    private boolean checkCity(MovieEvent event, String city) {
        return city == null || (StringUtils.equalsIgnoreCase(event.getTheater().getCinema().getCity(), city));
    }

    private boolean checkCinema(MovieEvent event, int cinema) {
        return cinema == -1 || (event.getTheater().getCinema().getId() == cinema);
    }

    private boolean checkDate(MovieEvent event, Date date) {
        return date == null || (event.getDate().compareTo(date) == 0);
    }

    private boolean checkTime(MovieEvent event, Date time) {
        return time == null || (event.getTime().compareTo(time) == 0);
    }
}
