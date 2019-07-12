package com.GoEvent.controller;



import com.GoEvent.model.Theater;
import com.GoEvent.service.impl.TheaterServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Log4j
@Controller
public class TheatresController {

    private TheaterServiceImpl theaterServiceImpl;


    @Autowired
    public void setTheaterServiceImpl(TheaterServiceImpl theaterServiceImpl) {
        this.theaterServiceImpl = theaterServiceImpl;
    }



    @RequestMapping(value = "/theaters", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("theaters", theaterServiceImpl.listAllTheaters());
        log.info("Returning rcinemas:");
        return "theaters/theaters";
    }

    @RequestMapping("theater/{id}")
    public String showProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("theater", theaterServiceImpl.getTheaterById(id));
        return "theaters/theatershow";
    }

    @RequestMapping("theater/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("theater", theaterServiceImpl.getTheaterById(id));
        return "theaters/theaterform";
    }

    @RequestMapping("theater/new")
    public String newProduct(Model model) {
        model.addAttribute("theater", new Theater());
        return "theaters/theaterform";
    }

    @RequestMapping(value = "theater", method = RequestMethod.POST)
    public String saveProduct(Theater theater) {
        theaterServiceImpl.saveTheater(theater);
        return "redirect:/theaters/" + theater.getId();
    }
    
    
    
    
    

//    @PostMapping("/add")
//    public Cinema addItem(@RequestBody Cinema cinema) {
//        return TheaterService.saveCinema(cinema);
//    }
//
//
//    @GetMapping("list")
//    public List<Cinema> getList() {
//        return TheaterService.cinemaList();
//    }
//
//
//    @GetMapping("update/{id}")
//    public Cinema showUpdateForm(@PathVariable("id") int id) {
//        return TheaterService.updateCinema(id);
//    }
//
//
//    @GetMapping("delete/{id}")
//    public String deleteStudent(@PathVariable("id") int id) {
//        return TheaterService.deleteCinema(id);
//    }

}
