package com.GoEvent.service.movies.impl;

import com.GoEvent.dao.movies.CinemaRepository;
import com.GoEvent.model.movies.Cinema;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Log4j
@Service
public class CinemaService implements com.GoEvent.service.movies.CinemaService {


    private CinemaRepository cinemaRepository;

    private static Lock lockRepository = new ReentrantLock();

    @Autowired
    public void setItemRepository(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public Cinema saveCinema(Cinema cinema) {
        lockRepository.lock();
        try {
            cinemaRepository.save(cinema);
        } finally {
            lockRepository.unlock();
        }
        return cinema;
    }

    @Override
    public List<Cinema> listAllCinema() {
        List<Cinema> cinemas;
        lockRepository.lock();
        try {
            cinemas = cinemaRepository.findAll();
        } finally {
            lockRepository.unlock();
        }
        return cinemas;
    }

    @Override
    public Cinema getCinemaById(Integer id) {
        Cinema cinema;
        lockRepository.lock();
        try {
            cinema = cinemaRepository.findById(id).orElse(null);
        } finally {
            lockRepository.unlock();
        }
        return cinema;
    }


    public boolean cinemaExists(String name, String city, String address) {
        List<Cinema> cinemas = cinemaRepository.findAll();
        for (Cinema cinema : cinemas)
            if (cinema.getName().equals(name) &&
                    cinema.getAddress().equals(address) &&
                    cinema.getCity().equals(city))
                return true;
        return false;
    }
}
