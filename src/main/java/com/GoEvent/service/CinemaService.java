package com.GoEvent.service;

import com.GoEvent.model.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CinemaService {



    Page<Cinema> findAllCinemasPageable(Pageable pageable);


    Iterable<Cinema> listAllCinema();

    Cinema getCinemaById(Integer id);

    Cinema saveCinema(Cinema product);
}
