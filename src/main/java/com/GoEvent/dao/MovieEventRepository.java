package com.GoEvent.dao;

import com.GoEvent.model.MovieEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieEventRepository extends JpaRepository<MovieEvent, Integer> {
}
