package com.GoEvent.dao;

import com.GoEvent.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    Optional<Theater> findById(int id);

    @Modifying
    @Query("DELETE FROM Theater t WHERE t.cinema.id = ?1")
    void deleteByCinemaId(int categoryId);

}
