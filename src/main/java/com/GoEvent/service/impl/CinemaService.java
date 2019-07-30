package com.GoEvent.service.impl;

import com.GoEvent.dao.CinemaRepository;
import com.GoEvent.model.Cinema;
import com.GoEvent.model.Movie;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j
@Service
public class CinemaService implements com.GoEvent.service.CinemaService {


   private CinemaRepository cinemaRepository ;


    @Autowired
    public void setItemRepository( CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public Cinema saveCinema(Cinema cinema) {
        cinemaRepository.save(cinema);
        log.info("the cinema "+cinema.getName()+" saved in the database");
        return cinema;
    }

    public List<Cinema> cinemaList() {
        return cinemaRepository.findAll();
    }

    public Cinema updateCinema(int id) {
        Optional<Cinema> cinema = cinemaRepository.findById(id);
//        if (cinema.isEmpty()) {
//            log.info("the cinema with the id " + id + " not found in the database.");
//            return new Cinema();
//        }
        log.info("the cinema "+cinema.get().getName()+" updated.");
        return cinema.get();
    }


    public String deleteCinema(int id) {
//        cinemaRepository.deleteById(id);
        log.info("the cinema with the id " + id + " deleted in the database.");
        return "success";
    }




    @Override
    public Page<Cinema> findAllCinemasPageable(Pageable pageable){
        return cinemaRepository.findAll(pageable);
    }



    @Override
    public Iterable<Cinema> listAllCinema() {
        return cinemaRepository.findAll();
    }

    @Override
    public Cinema getCinemaById(Integer id) {
        return cinemaRepository.findById(id).orElse(null);
    }




}
