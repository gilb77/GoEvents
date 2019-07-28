package com.GoEvent.service;

import com.GoEvent.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {



    Iterable<Movie> listAllProducts();

    Movie getProductById(Integer id);


}
