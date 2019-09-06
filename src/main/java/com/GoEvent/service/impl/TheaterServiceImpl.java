package com.GoEvent.service.impl;


import com.GoEvent.dao.TheaterRepository;
import com.GoEvent.model.Theater;
import com.GoEvent.service.TheaterService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class TheaterServiceImpl implements TheaterService {

    private TheaterRepository theaterRepository;

    @Autowired
    public void setItemRepository(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }


    @Override
    public Page<Theater> findAllTheatersPageable(Pageable pageable) {
        return theaterRepository.findAll(pageable);
    }

    @Override
    public Iterable<Theater> listAllTheaters() {
        return theaterRepository.findAll();
    }

    @Override
    public Theater getTheaterById(Integer id) {
        return theaterRepository.findById(id).orElse(null);
    }

    @Override
    public Theater saveTheater(Theater theater) {
        theaterRepository.save(theater);
        log.info("the theater " + theater.getId() + " saved in the database");
        return theater;
    }


    public String deleteTheater(int id) {
//        theaterRepository.deleteById(id);
        theaterRepository.deleteByCinemaId(id);
        log.info("the movie with the id " + id + " deleted in the database.");
        return "success";
    }
}
