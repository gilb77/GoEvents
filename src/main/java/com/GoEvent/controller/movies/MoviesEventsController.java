package com.GoEvent.controller.movies;

import com.GoEvent.model.movies.MovieEvent;
import com.GoEvent.service.movies.impl.CinemaService;
import com.GoEvent.service.movies.impl.MovieEventServiceImpl;
import com.GoEvent.service.movies.impl.MovieServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import static com.GoEvent.util.WebUtil.responseForError;


@Log4j
@Controller
@RequestMapping(value = "/event")
public class MoviesEventsController {

    private MovieEventServiceImpl movieEventService;

    private CinemaService cinemaService;


    private MovieServiceImpl movieService;

    @Autowired
    public void createMovieEventService(MovieEventServiceImpl movieEventService,
                                        CinemaService cinemaService,
                                        MovieServiceImpl movieService) {
        this.movieEventService = movieEventService;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
    }


    @RequestMapping(value = "/movie/new", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("cinemas", this.cinemaService.listAllCinema());
        model.addAttribute("movies", movieService.listAllMovies());
        model.addAttribute("movieEvent", new MovieEvent());
        return "events/movievent";
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteEvent(@PathVariable("id") int id, Model model) {
        if (!movieEventService.deleteMovieEventById(id)) {
            model.addAttribute("error", "The event was invited by users");
            model.addAttribute("events", movieEventService.listAllEvents());
            return "events/movieseventstable";
        }
        return goTable(model);
    }


    @RequestMapping(value = "/cinema/{id}")
    public String chooseCinema(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("theaters", cinemaService.getCinemaById(id).getTheatersList());
        return "events/selectevent :: theatersFragment";
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String getNewEvent(@RequestBody Map<String, String> json, HttpServletResponse res) throws ParseException, IOException {
        if (json.get("theater").isEmpty() || json.get("time").isEmpty())
            return responseForError(res, "No events");
        if (!movieEventService.saveMovieEvent(json))
            return responseForError(res, "Events exists");
        return responseForError(res, "success");
    }


    @RequestMapping(value = "/movies/table", method = RequestMethod.POST)
    public String GetEvents(Model model) {
        model.addAttribute("events", movieEventService.listAllEvents());
        return "events/movieseventstable";
    }

    @RequestMapping(value = "/movies/table")
    public String goTable(Model model) {
        model.addAttribute("events", movieEventService.listAllEvents());
        return "events/movieseventstable";
    }


}
