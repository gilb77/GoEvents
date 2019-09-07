package com.GoEvent.dao.movies;

import com.GoEvent.model.movies.MovieEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieEventRepository extends JpaRepository<MovieEvent, Integer> {
}
