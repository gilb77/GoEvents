package com.GoEvent.service;


import com.GoEvent.model.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TheaterService {

    Page<Theater> findAllTheatersPageable(Pageable pageable);


    Iterable<Theater> listAllTheaters();

    Theater getTheaterById(Integer id);

    Theater saveTheater(Theater product);

}
