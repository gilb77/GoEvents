package com.GoEvent.service.movies;


import com.GoEvent.model.movies.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TheaterService {



    Iterable<Theater> listAllTheaters();

    Theater getTheaterById(Integer id);

    Theater saveTheater(Theater product);

}
