package com.GoEvent.service.movies.impl;


import com.GoEvent.dao.movies.MovieEventRepository;
import com.GoEvent.model.Invitation;
import com.GoEvent.model.movies.MovieEvent;
import com.GoEvent.service.EventService;
import com.GoEvent.util.ParseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class MovieEventServiceImpl implements EventService {

    @Autowired
    private MovieEventRepository movieEventRepository;
    @Autowired
    private InvitationServiceImpl invitationService;

    @Autowired
    private TheaterServiceImpl theaterService;

    @Autowired
    private MovieServiceImpl movieService;

    public static Lock lockRepository = new ReentrantLock();

    public synchronized boolean deleteMovieEventById(int id) {
        InvitationServiceImpl.globalLockRepository.lock();
        lockRepository.lock();
        try {
            for (Invitation invitation : invitationService.getAllIvitations())
                if (invitation.getEventId() == id && movieEventRepository.findById(id).get().getDate().after(new Date())) {
                    lockRepository.unlock();
                    InvitationServiceImpl.globalLockRepository.unlock();
                    return false;
                }
            movieEventRepository.deleteById(id);
        } finally {
            lockRepository.unlock();
            InvitationServiceImpl.globalLockRepository.unlock();
        }
        return true;
    }

    public void saveEvent(MovieEvent movieEvent) {
        lockRepository.lock();
        this.movieEventRepository.save(movieEvent);
        lockRepository.unlock();
    }

    public boolean saveMovieEvent(Map<String, String> json) throws ParseException {
        if (checkMovieEventNotHappenAtSameTime(json))
            return false;
        MovieEvent movieEvent = new MovieEvent();
        movieEvent.setDate(ParseUtil.parseStringToDate(json.get("date")));
        movieEvent.setTime(ParseUtil.parseStringToTime(json.get("time")));
        movieEvent.setMovie(movieService.getMovieById(Integer.parseInt(json.get("movie"))));
        movieEvent.setTheater(theaterService.getTheaterById(Integer.parseInt(json.get("theater"))));
        movieEvent.setPrice(Integer.parseInt(json.get("price")));
        movieEvent.getSeat().setSeats(movieEvent.getTheater().getSeats());
        saveEvent(movieEvent);
        return true;
    }


    private boolean checkMovieEventNotHappenAtSameTime(Map<String, String> json) {
        lockRepository.lock();
        try {
            List<MovieEvent> movieEvents = getMovieEventByFilter(Integer.parseInt(json.get("movie")),
                    theaterService.getTheaterById(Integer.parseInt(json.get("theater"))).getCinema().getId(),
                    ParseUtil.parseStringToDate(json.get("date")),
                    ParseUtil.parseStringToTime(json.get("time")));
            if (movieEvents.isEmpty()) {
                lockRepository.unlock();
                return true;
            }
            for (MovieEvent movieEvent : movieEvents)
                if (movieEvent.getTheater().getId() == Integer.parseInt(json.get("theater"))) {
                    lockRepository.unlock();
                    return false;
                }
        } finally {
            lockRepository.unlock();
            return true;
        }

    }

    public MovieEvent findMovieEventById(int id) {
        return movieEventRepository.findById(id).get();
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

    public List<MovieEvent> getMovieEventByFilter(int movie, int cinema, Date date, Date time) {
        return getMovieEventByFilter(movie, null, cinema, date, time);
    }


    public List<MovieEvent> getMovieEventByFilter(int movie, String city, int cinema, Date date, Date time) {
        lockRepository.lock();
        List<MovieEvent> movieEvents, tempMovieEvents;
        try {
            movieEvents = movieEventRepository.findAll();
            tempMovieEvents = new ArrayList<>();
            for (MovieEvent event : movieEvents) {
                if (checkMovie(event, movie) && checkCity(event, city) &&
                        checkCinema(event, cinema) && checkDate(event, date) && checkTime(event, time))
                    tempMovieEvents.add(event);
            }
        } finally {
            lockRepository.unlock();
        }
        return tempMovieEvents;
    }

    public List<Integer> listAllSeatsByTime(int movie, String city, int cinema, Date date, Date time) throws Exception {
        return getMovieEvent(movie, city, cinema, date, time).getSeat().getFreeSeats();
    }

    public MovieEvent getMovieEvent(int movie, String city, int cinema, Date date, Date time) {
        List<MovieEvent> movieEvent = getMovieEventByFilter(movie, city, cinema, date, time);
        if (movieEvent.isEmpty() || movieEvent.size() > 1)
            return null;
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

    public List<MovieEvent> buildCityList(List<MovieEvent> movieEvents) {
        List<MovieEvent> tempMovieEvents = new ArrayList<>();
        List<String> cites = new ArrayList<>();

        for (MovieEvent event : movieEvents)
            cites.add(event.getTheater().getCinema().city);
        cites = new ArrayList<>(new HashSet<>(cites));
        for (MovieEvent event : movieEvents)
            if (cites.contains(event.getTheater().getCinema().city)) {
                tempMovieEvents.add(event);
                cites.remove(event.getTheater().getCinema().city);
            }
        return tempMovieEvents;
    }


    public List<MovieEvent> buildCinemaiList(List<MovieEvent> movieEvents) {
        List<MovieEvent> tempMovieEvents = new ArrayList<>();
        List<String> location = new ArrayList<>();
        for (MovieEvent event : movieEvents)
            location.add(event.getTheater().getCinema().name + " " + event.getTheater().getCinema().address);
        location = new ArrayList<>(new HashSet<>(location));
        for (MovieEvent event : movieEvents)
            if (location.contains(event.getTheater().getCinema().name + " " + event.getTheater().getCinema().address)) {
                tempMovieEvents.add(event);
                location.remove(event.getTheater().getCinema().name + " " + event.getTheater().getCinema().address);
            }
        return tempMovieEvents;
    }


    public List<MovieEvent> buildDateList(List<MovieEvent> movieEvents) {
        List<MovieEvent> tempMovieEvents = new ArrayList<>();
        List<Date> dates = new ArrayList<>();
        for (MovieEvent event : movieEvents)
            dates.add(event.getDate());
        dates = new ArrayList<>(new HashSet<>(dates));
        for (MovieEvent event : movieEvents)
            if (dates.contains(event.getDate())) {
                tempMovieEvents.add(event);
                dates.remove(event.getDate());
            }
        return tempMovieEvents;
    }


    public boolean checkEventOntheFuture(int id) {
        lockRepository.lock();
        try {

            for (MovieEvent movieEvent : getMovieEventByFilter(id))
                for (Invitation invitation : invitationService.getAllIvitations())
                    if (invitation.getEventId() == movieEvent.getId() && movieEvent.getDate().after(new Date()))
                        return true;
        } finally {
            lockRepository.unlock();
        }
        return false;
    }


    public boolean deleteEventByMovieId(int id) {
        for (MovieEvent movieEvent : getMovieEventByFilter(id))
            if (deleteMovieEventById(movieEvent.getId()))
                return false;
        return true;
    }
}
