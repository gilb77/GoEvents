package com.GoEvent.dao;

import com.GoEvent.model.MovieInvite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieInviteRepository  extends JpaRepository<MovieInvite, Integer> {
}
