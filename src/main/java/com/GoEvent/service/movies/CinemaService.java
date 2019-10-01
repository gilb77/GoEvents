package com.GoEvent.service.movies;

import com.GoEvent.model.movies.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CinemaService {






    Iterable<Cinema> listAllCinema();

    Cinema getCinemaById(Integer id);

    Cinema saveCinema(Cinema product);
}
