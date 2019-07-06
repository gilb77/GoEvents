package com.GoEvent.controller;

import com.GoEvent.model.Movie;
import com.GoEvent.service.impl.MovieServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j
@Controller
public class MovieController {


    private MovieServiceImpl movieService;


    @Autowired
    public void setProductsService(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }


    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("movies", movieService.listAllProducts());
        log.info("Returning rmovies:");
        return "movies";
    }

    @RequestMapping("movie/{id}")
    public String showProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("movie", movieService.getProductById(id));
        return "movieshow";
    }

    @RequestMapping("movie/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("movie", movieService.getProductById(id));
        return "movieform";
    }

    @RequestMapping("movie/new")
    public String newProduct(Model model) {
        model.addAttribute("movie", new Movie());
        return "movieform";
    }

    @RequestMapping(value = "movie", method = RequestMethod.POST)
    public String saveProduct(Movie movie) {
        movieService.saveProduct(movie);
        return "redirect:/movie/" + movie.getId();
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
