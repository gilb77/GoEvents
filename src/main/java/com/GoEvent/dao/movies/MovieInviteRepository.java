package com.GoEvent.dao.movies;

import com.GoEvent.model.movies.MovieInvite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieInviteRepository  extends JpaRepository<MovieInvite, Integer> {
}
