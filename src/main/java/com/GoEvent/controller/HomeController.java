package com.GoEvent.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class HomeController {


    @RequestMapping("/")
    String index(){
        return "index";
    }

    @RequestMapping("/home")
    String home(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!----"+roles+"-----!!!!!!!!!!!!!!!!!!!!!");
        return "index";
    }

}
