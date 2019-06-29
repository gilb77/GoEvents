package com.GoEvent.controller;


import com.GoEvent.model.Cinema;
import com.GoEvent.service.impl.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cinemas/")
public class CinemaController {

    private CinemaService cinemaService;


    @Autowired
    public void setProductsService(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping("/add")
    public Cinema addItem(@RequestBody Cinema cinema) {
        return cinemaService.saveCinema(cinema);
    }


    @GetMapping("list")
    public List<Cinema> getList() {
        return cinemaService.cinemaList();
    }


    @GetMapping("update/{id}")
    public Cinema showUpdateForm(@PathVariable("id") int id) {
        return cinemaService.updateCinema(id);
    }


    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        return cinemaService.deleteCinema(id);
    }

}
