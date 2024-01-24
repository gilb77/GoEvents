package com.GoEvent.service.movies.impl;


import com.GoEvent.dao.movies.TheaterRepository;
import com.GoEvent.model.movies.Movie;
import com.GoEvent.model.movies.MovieEvent;
import com.GoEvent.model.movies.Theater;
import com.GoEvent.service.movies.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class TheaterServiceImpl implements TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private MovieEventServiceImpl movieEventService;

    @Autowired
    private MovieServiceImpl movieService;


    private static Lock lockRepository = new ReentrantLock();


    @Override
    public List<Theater> listAllTheaters() {
        List<Theater> theaters;
        lockRepository.lock();
        try {
            theaters = theaterRepository.findAll();
        } finally {
            lockRepository.unlock();
        }
        return theaters;
    }

    @Override
    public Theater getTheaterById(Integer id) {
        Theater theater;
        lockRepository.lock();
        try {
            theater = theaterRepository.findById(id).orElse(null);
        } finally {
            lockRepository.unlock();
        }
        return theater;
    }

    @Override
    public Theater saveTheater(Theater theater) {
        lockRepository.lock();
        try {
            theater = theaterRepository.save(theater);
        } finally {
            lockRepository.unlock();
        }
        return theater;
    }


    public String deleteTheater(int cinemaId, int  theaterId) {
        lockRepository.lock();
        try {
            for (Movie movie : movieService.listAllMovies())
                for (MovieEvent movieEvent : movieEventService.getMovieEventByFilter(movie.getId(), null,cinemaId))
                    if (movieEvent.getTheater().getId() == theaterId)
                        movieEventService.deleteMovieEventById(movieEvent.getId());
            theaterRepository.deleteById(theaterId);
        } finally {
            lockRepository.unlock();
        }
        return "success";
    }
}
