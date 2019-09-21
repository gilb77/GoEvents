package com.GoEvent.controller.movies;


import com.GoEvent.model.movies.Movie;
import com.GoEvent.model.movies.MovieEvent;
import com.GoEvent.service.impl.ShoppingCartServiceImpl;
import com.GoEvent.service.movies.impl.MovieEventServiceImpl;
import com.GoEvent.service.movies.impl.MovieServiceImpl;
import com.GoEvent.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static com.GoEvent.util.ParseUtil.parseStringToDate;
import static com.GoEvent.util.ParseUtil.parseStringToTime;
import static com.GoEvent.util.WebUtil.responseForError;


@Controller
public class MovieInviteController {


    private MovieServiceImpl movieService;
    private MovieEventServiceImpl movieEventService;

    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;

    @Autowired
    public void setProductsService(MovieServiceImpl movieService,
                                   MovieEventServiceImpl movieEventService) {
        this.movieService = movieService;
        this.movieEventService = movieEventService;
    }


    @PostMapping("/movie/invite")
    public String inviteMovie(@ModelAttribute Movie movie, Model model) {
        model.addAttribute("movie", movie);
        model.addAttribute("eventsByMovie", movieEventService.getMovieEventByFilter(movie.getId()));
        return "movie/movie-invite";
    }


    @RequestMapping(value = "/movie/invite/{movie}")
    public String inviteMovie(@PathVariable("movie") int movie, Model model, HttpServletResponse res) throws Exception {
        model.addAttribute("movie", movieService.getMovieById(movie));
        List<MovieEvent> movieEvents = movieEventService.getMovieEventByFilter(movie);
        if (movieEvents.isEmpty())
            return responseForError(res,"No events");
        model.addAttribute("eventsByMovie", movieEventService.buildCityList(movieEvents));
        return "movie/movie-invite-fragments :: cityFragment";
    }

    @RequestMapping(value = "/movie/invite/{movie}/{city}")
    public String chooseCinema(@PathVariable("movie") int movie, @PathVariable("city") String city,
                               Model model, HttpServletResponse res) throws IOException {
        List<MovieEvent> movieEvents = movieEventService.getMovieEventByFilter(movie, city);
        if (movieEvents.isEmpty())
            return responseForError(res,"No events");
        model.addAttribute("eventsByCity", movieEventService.buildCinemaiList(movieEvents));
        return "movie/movie-invite-fragments :: cinemasFragment";
    }


    @RequestMapping(value = "/movie/invite/{movie}/{city}/{cinema}")
    public String chooseDate(@PathVariable("movie") int movie, @PathVariable("city") String city,
                             @PathVariable("cinema") int cinema, Model model, HttpServletResponse res)
            throws IOException {
        List<MovieEvent> movieEvents = movieEventService.getMovieEventByFilter(movie, city, cinema);
        if (movieEvents.isEmpty())
            return responseForError(res,"No events");
        model.addAttribute("eventsByCinema", movieEventService.buildDateList(movieEvents));
        model.addAttribute("parseUtil", new ParseUtil());
        return "movie/movie-invite-fragments :: dateFragment";
    }

    @RequestMapping(value = "/movie/invite/fillUpTime", method = RequestMethod.POST)
    public String chooseTime(@RequestBody Map<String, String> json, Model model, HttpServletResponse res)
            throws IOException, ParseException {
        List<MovieEvent> movieEvents = movieEventService.getMovieEventByFilter(
                Integer.parseInt(json.get("movie")), json.get("city"), Integer.parseInt(json.get("cinema")),
                parseStringToDate(json.get("date")));
        if (movieEvents.isEmpty())
            return responseForError(res,"No events");
        model.addAttribute("eventsByDate", movieEvents);
        model.addAttribute("parseUtil", new ParseUtil());
        return "movie/movie-invite-fragments :: timeFragment";
    }


    @RequestMapping(value = "movie/invite/fillUpSeats", method = RequestMethod.POST)
    public String chooseSeat(@RequestBody Map<String, String> json,
                             ModelMap model, HttpServletResponse res) throws Exception {
        List<Integer> seats = movieEventService.listAllSeatsByTime(Integer.parseInt(json.get("movie")), json.get("city"),
                Integer.parseInt(json.get("cinema")), parseStringToDate(json.get("date")),
                parseStringToTime(json.get("time")));
        if (seats.isEmpty())
            return responseForError(res,"No events");
        model.addAttribute("freeSeats", seats);
        return "movie/movie-invite-fragments :: seatFragment";
    }

    @RequestMapping(value = "/movie/invite/new", method = RequestMethod.POST)
    public String newInvite(@RequestBody Map<String, String> json,
                            ModelMap model, HttpServletResponse res) throws Exception {
        MovieEvent movieEvents = movieEventService.getMovieEvent(Integer.parseInt(json.get("movie")),
                json.get("city"), Integer.parseInt(json.get("cinema")),
                parseStringToDate(json.get("date")), parseStringToTime(json.get("time")));
        if (movieEvents == null)
            return responseForError(res,"No events");
        shoppingCartService.addEventInvites(movieEvents, Integer.parseInt(json.get("seat")), false);
        return "Success add event to the shopping cart .";
    }



}
