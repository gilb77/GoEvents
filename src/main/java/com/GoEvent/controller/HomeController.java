package com.GoEvent.controller;

import com.GoEvent.model.Movie;
import com.GoEvent.service.impl.MovieServiceImpl;
import com.GoEvent.util.Pager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

//@Controller
public class HomeController {

//    private static final int INITIAL_PAGE = 0;
//
//    private final MovieServiceImpl movieService;
//
//    @Autowired
//    public HomeController(MovieServiceImpl movieService ) {
//        this.movieService = movieService;
//    }
//
//    @GetMapping("/home")
//    public ModelAndView home(@RequestParam("page") Optional<Integer> page) {
//
//        // Evaluate page. If requested parameter is null or less than 0 (to
//        // prevent exception), return initial size. Otherwise, return value of
//        // param. decreased by 1.
//        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
//
//        Page<Movie> movie = this.movieService.findAllMoviesPageable(new PageRequest(evalPage, 5));
//        Pager pager = new Pager(movie);
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("products", movie);
//        modelAndView.addObject("pager", pager);
//        modelAndView.setViewName("/home");
//        return modelAndView;
//    }

}
