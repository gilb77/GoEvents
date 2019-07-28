package com.GoEvent.controller;

import com.GoEvent.model.MovieEvent;
import com.GoEvent.service.impl.CinemaService;
import com.GoEvent.service.impl.MovieEventServiceImpl;
import com.GoEvent.service.impl.MovieServiceImpl;
import com.GoEvent.service.impl.TheaterServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Log4j
@Controller
@RequestMapping(value = "/event")
public class MoviesEventsController {

    private MovieEventServiceImpl movieEventService;

    private CinemaService cinemaService;

    private TheaterServiceImpl theaterService;

    private MovieServiceImpl movieService;

    @Autowired
    public void createMovieEventService(MovieEventServiceImpl movieEventService,
                                        CinemaService cinemaService,
                                        MovieServiceImpl movieService, TheaterServiceImpl theaterService) {
        this.movieEventService = movieEventService;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
        this.theaterService = theaterService;
    }

    @RequestMapping(value = "/movie/new", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("cinemas", this.cinemaService.listAllCinema());
        model.addAttribute("movies", movieService.listAllProducts());
        model.addAttribute("movieEvent", new MovieEvent());
        return "events/movievent";
    }


    @RequestMapping(value = "/cinema/{id}")
    public String chooseCinema(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("theaters", cinemaService.getCinemaById(id).getTheatersList());
        return "events/selectevent :: theatersFragment";
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public void getNewEvent(@RequestBody Map<String, String> json) throws ParseException {
        System.out.println(json.get("movie"));
        DateFormat formatter = new SimpleDateFormat("MM/DD/yyyy");
        Date date = formatter.parse(json.get("date") );
        formatter = new SimpleDateFormat("HH:mm");
        Date time = formatter.parse(json.get("time"));
        MovieEvent movieEvent = new MovieEvent();
        movieEvent.setDate(date);
        movieEvent.setTime(time);
        movieEvent.setMovie(movieService.getProductById(Integer.parseInt(json.get("movie"))));
        movieEvent.setTheater(theaterService.getTheaterById(Integer.parseInt(json.get("theater"))));
        movieEvent.setPrice(json.get("price"));
        movieEventService.saveEvent(movieEvent);
    }


//    @RequestMapping(value = "load", method = RequestMethod.POST)
//    public String saveProduct( )  {
//        return "redirect:/event/movies/table";
//    }

    @RequestMapping(value = "/movies/table", method = RequestMethod.POST)
    public String GetEvents(Model model) {
        model.addAttribute("events", movieEventService.listAllEvents());
        return "events/movieseventstable";
    }

    @RequestMapping(value = "/movies/table" )
    public String goTable(Model model) {
        model.addAttribute("events", movieEventService.listAllEvents());
        return "events/movieseventstable";
    }


}
