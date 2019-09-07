package com.GoEvent.controller.movies;


import com.GoEvent.model.movies.Cinema;
import com.GoEvent.service.movies.impl.CinemaService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Log4j
@Controller
public class CinemaController {

    private CinemaService cinemaService;


    @Autowired
    public void setProductsService(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }



    @RequestMapping(value = "/cinemas", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("cinemas", cinemaService.listAllCinema());
        log.info("Returning rcinemas:");
        return "cinema/cinemas";
    }

    @RequestMapping("cinema/{id}")
    public String showProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("cinema", cinemaService.getCinemaById(id));
        return "cinema/cinemashow";
    }

    @RequestMapping("cinema/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("cinema", cinemaService.getCinemaById(id));
        return "cinema/cinemaform";
    }

    @RequestMapping("cinema/new")
    public String newProduct(Model model) {
        model.addAttribute("cinema", new Cinema());
        return "cinema/cinemaform";
    }

    @RequestMapping(value = "cinema", method = RequestMethod.POST)
    public String saveProduct(Cinema cinema) {
        cinemaService.saveCinema(cinema);
        return "redirect:/cinema/" + cinema.getId();
    }



}
