package com.GoEvent.controller;

import com.GoEvent.model.Movie;
import com.GoEvent.service.impl.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/movies/")
public class MovieController {


    private MovieService movieService;



    @Autowired
    public void setProductsService(MovieService  movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public Movie addItem(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }


    @GetMapping("list")
    public List<Movie> getList() {
        return movieService.movieList();
    }


    @GetMapping("update/{id}")
    public Movie showUpdateForm(@PathVariable("id") int id) {
     return movieService.updateMovie(id);
    }


    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        return movieService.deleteMovie(id);
    }


}
