package com.GoEvent.controller.movies;


import com.GoEvent.model.movies.Movie;
import com.GoEvent.service.movies.impl.MovieEventServiceImpl;
import com.GoEvent.service.movies.impl.MovieInviteServiceImpl;
import com.GoEvent.service.movies.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

import static com.GoEvent.util.ParseUtil.parseStringToDate;
import static com.GoEvent.util.ParseUtil.parseStringToTime;


@Controller
public class MovieInviteController {


    private MovieServiceImpl movieService;
    private MovieEventServiceImpl movieEventService;
    private MovieInviteServiceImpl movieInviteService;

    @Autowired
    public void setProductsService(MovieServiceImpl movieService,
                                   MovieEventServiceImpl movieEventService,
                                   MovieInviteServiceImpl movieInviteService) {
        this.movieService = movieService;
        this.movieEventService = movieEventService;
        this.movieInviteService = movieInviteService;
    }


    @PostMapping("/movie/invite")
    public String inviteMovie(@ModelAttribute Movie movie, Model model) {
        model.addAttribute("movie", movie);
        model.addAttribute("eventsByMovie", movieEventService.listAllEventsByMovie(movie.getId()));
        return "movie/movie-invite";
    }


    @RequestMapping(value = "/movie/invite/{movie}")
    public String inviteMovie(@PathVariable("movie") int movie, Model model) {
        model.addAttribute("movie", movieService.getMovieById(movie));
        model.addAttribute("eventsByMovie", movieEventService.listAllEventsByMovie(movie));
        return "movie/movie-invite-fragments :: cityFragment";
    }

    @RequestMapping(value = "/movie/invite/{movie}/{city}")
    public String chooseCinema(@PathVariable("movie") int movie, @PathVariable("city") String city, ModelMap model) {
        model.addAttribute("eventsByCity", movieEventService.listAllEventByCity(movie, city));
        return "movie/movie-invite-fragments :: cinemasFragment";
    }


    @RequestMapping(value = "/movie/invite/{movie}/{city}/{cinema}")
    public String chooseDate(@PathVariable("movie") int movie,
                             @PathVariable("city") String city,
                             @PathVariable("cinema") int cinema,
                             ModelMap model) {
        model.addAttribute("eventsByCinema", movieEventService.listAllEventByCinema(movie, city, cinema));
        return "movie/movie-invite-fragments :: dateFragment";
    }

    @RequestMapping(value = "/movie/invite/fillUpTime", method = RequestMethod.POST)
    public String chooseTime(@RequestBody Map<String, String> json,
                             ModelMap model) throws ParseException {
        model.addAttribute("eventsByDate",
                movieEventService.listAllEventByDate(
                        Integer.parseInt(json.get("movie")),
                        json.get("city"),
                        Integer.parseInt(json.get("cinema")),
                        parseStringToDate(json.get("date"))));
        return "movie/movie-invite-fragments :: timeFragment";
    }


    @RequestMapping(value = "movie/invite/fillUpSeats", method = RequestMethod.POST)
    public String chooseSeat(@RequestBody Map<String, String> json,
                             ModelMap model) throws Exception {
        model.addAttribute("freeSeats",
                movieEventService.listAllSeatsByTime(
                        Integer.parseInt(json.get("movie")),
                        json.get("city"),
                        Integer.parseInt(json.get("cinema")),
                        parseStringToDate(json.get("date")),
                        parseStringToTime(json.get("time"))));
        return "movie/movie-invite-fragments :: seatFragment";
    }

    @RequestMapping(value = "/movie/invite/new", method = RequestMethod.POST)
    public String newInvite(@RequestBody Map<String, String> json,
                            ModelMap model) throws Exception {
        movieInviteService.newInvite(
                Integer.parseInt(json.get("movie")),
                json.get("city"),
                Integer.parseInt(json.get("cinema")),
                parseStringToDate(json.get("date")),
                parseStringToTime(json.get("time")),
                json.get("seat"));
        return "";
    }
}
