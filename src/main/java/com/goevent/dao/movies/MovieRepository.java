package com.GoEvent.dao.movies;


import com.GoEvent.model.movies.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> findById(int id);
}
