package com.GoEvent.controller;


import com.GoEvent.service.liveshows.ArtistServiceImpl;
import com.GoEvent.service.movies.impl.MovieServiceImpl;
import com.GoEvent.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

    @Autowired
    private ArtistServiceImpl artistService;

    @Autowired
    private MovieServiceImpl movieService;

    @RequestMapping("/")
    public String index(Model model) {
        return home(model);
    }

    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("movies", movieService.listAllMovies());
        model.addAttribute("artists", artistService.listAllArtist());
        model.addAttribute("parseUtil", new ParseUtil());
        return "index";
    }

}
