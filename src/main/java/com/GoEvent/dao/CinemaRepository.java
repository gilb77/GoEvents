package com.GoEvent.dao;

import com.GoEvent.model.Cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    Optional<Cinema> findById(int id);
}
