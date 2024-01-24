package com.GoEvent.controller.movies;

import com.GoEvent.model.movies.Movie;
import com.GoEvent.service.movies.impl.MovieServiceImpl;
import com.GoEvent.util.ParseUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


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
        model.addAttribute("movies", movieService.listAllMovies());
        log.info("Returning rmovies:");
        return "movie/movies";
    }

    @RequestMapping(value = "/moviesPage", method = RequestMethod.GET)
    public String pageMovie(Model model) {
        model.addAttribute("movies", movieService.listAllMovies());
        return "movie/movieslist";
    }

    @RequestMapping("movie/{id}")
    public String showProduct(@PathVariable Integer id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        model.addAttribute("parseUtil", new ParseUtil());
        return "movie/movieshow";
    }

    @RequestMapping("movie/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "movie/movieform";
    }

    @RequestMapping("movie/new")
    public String newProduct(Model model) {
        model.addAttribute("movie", new Movie());
        return "movie/movieform";
    }

    @RequestMapping(value = "movie", method = RequestMethod.POST)
    public String saveProduct(Movie movie, Model model,
                              @RequestParam MultipartFile fileUpload) throws IOException {

        if (movie.getName().isEmpty() || movie.getDescription().isEmpty() || fileUpload.isEmpty() || movie.getReview().isEmpty()
                || movie.getVedio().isEmpty()) {
            model.addAttribute("error", "One of the field is empty.");
            return "movie/movieform";
        }

        if (fileUpload.getContentType().equals("image/png") || fileUpload.getContentType().equals("image/jpg")
                || fileUpload.getContentType().equals("image/jpeg") || fileUpload.getContentType().equals("image/gif")) {
            movie.setImage(fileUpload.getBytes());
        }

        if (!movieService.saveMovie(movie)) {
            model.addAttribute("error", "Movie name exists.");
            return "movie/movieform";
        }
        return "redirect:/movie/" + movie.getId();
    }


    @RequestMapping("movie/delete/{id}")
    public String deleteMovie(@PathVariable Integer id, Model model) {
        if (!movieService.deleteMovie(id)) {
            model.addAttribute("error", "The movie was invited by users");
            model.addAttribute("movies", movieService.listAllMovies());
            return "movie/movies";
        }
            return "redirect:/movies";
    }


}
