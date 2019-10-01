package com.GoEvent.service.movies.impl;

import com.GoEvent.dao.movies.MovieRepository;
import com.GoEvent.model.movies.Movie;
import com.GoEvent.service.movies.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieEventServiceImpl movieEventService;

    private static Lock lockRepository = new ReentrantLock();

    public boolean saveMovie(Movie movie) {
        lockRepository.lock();
        try {
            if (movieNameExists(movie.getName()) && movie.getId() == 0)
                return false;
            movieRepository.save(movie);
        } finally {
            lockRepository.unlock();
        }
        return true;
    }


    public boolean deleteMovie(int id) {
        lockRepository.lock();
        try {
            if (!movieEventService.deleteEventByMovieId(id))
                return false;
            movieRepository.deleteById(id);
        } finally {
            lockRepository.unlock();
        }
        return true;
    }


    @Override
    public List<Movie> listAllMovies() {
        List<Movie> movies;
        lockRepository.lock();
        try {
            movies = movieRepository.findAll();
        } finally {
            lockRepository.unlock();
        }
        return movies;
    }

    @Override
    public Movie getMovieById(Integer id) {
        Movie movie;
        lockRepository.lock();
        try {
            movie = movieRepository.findById(id).orElse(null);
        } finally {
            lockRepository.unlock();
        }
        return movie;
    }


    private boolean movieNameExists(String name) {
        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies)
            if (movie.getName().equals(name))
                return true;
        return false;
    }


}
