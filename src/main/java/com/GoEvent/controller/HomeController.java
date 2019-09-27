package com.GoEvent.controller;


import com.GoEvent.configuration.SpringSecurityConfig;
import com.GoEvent.service.movies.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class HomeController {

    @Autowired
    private MovieServiceImpl movieService;
    @RequestMapping("/")
    String index(){
        return "index";
    }

    @RequestMapping("/home")
    public String home(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());
        model.addAttribute("movies",movieService.listAllMovies());
        System.out.println("-----------------------------");
        return "index";
    }

}
