package com.GoEvent.dao.liveshows;


import com.GoEvent.model.liveshows.LiveShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveShowRepository extends JpaRepository<LiveShow, Integer> {
}
