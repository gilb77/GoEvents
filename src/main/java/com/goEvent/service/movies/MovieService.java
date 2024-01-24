package com.GoEvent.service.movies;

import com.GoEvent.model.movies.Movie;

public interface MovieService {



    Iterable<Movie> listAllMovies();

    Movie getMovieById(Integer id);


}
