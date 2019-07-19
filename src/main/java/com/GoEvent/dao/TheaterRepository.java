package com.GoEvent.dao;

import com.GoEvent.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {
//    public Theater findByName(String name);
}
