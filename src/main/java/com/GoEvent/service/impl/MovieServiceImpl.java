package com.GoEvent.service.impl;

import com.GoEvent.dao.MovieRepository;
import com.GoEvent.model.Movie;
import com.GoEvent.service.MovieService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j
@Service
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;

    @Autowired
    public void setItemRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie saveMovie(Movie movie) {
        movieRepository.save(movie);
        log.info("the movie " + movie.getName() + " save in the database.");
        return movie;
    }

    public List<Movie> movieList() {
        return movieRepository.findAll();
    }

    public Movie updateMovie(int id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            log.info("the movie with the id " + id + " not found in the database.");
            return new Movie();
        }
        log.info("the movie "+movie.get().getName()+" updated.");
        return movie.get();
    }



    public  Page<Movie> findAllMoviesPageable(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }


    public String deleteMovie(int id) {
        movieRepository.deleteById(id);
        log.info("the movie with the id " + id + " deleted in the database.");
        return "success";
    }



    @Override
    public Iterable<Movie> listAllProducts() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getProductById(Integer id) {
        return movieRepository.findById(id).orElse(null);
    }


    public Movie saveProduct(Movie product) {
        return movieRepository.save(product);
    }


}
