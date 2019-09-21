package com.GoEvent.controller.movies;



import com.GoEvent.model.movies.Theater;
import com.GoEvent.service.movies.impl.CinemaService;
import com.GoEvent.service.movies.impl.TheaterServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Log4j
@Controller
public class TheatresController {

    private TheaterServiceImpl theaterServiceImpl;

    private CinemaService cinemaService;


    @Autowired
    public void setServices(TheaterServiceImpl theaterServiceImpl,CinemaService cinemaService) {
        this.theaterServiceImpl = theaterServiceImpl;
        this.cinemaService = cinemaService;
    }


    @RequestMapping(value = "theaters/cinema/{id}")
    public String chooseCinema(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("theaters", cinemaService.getCinemaById(id).getTheatersList());
        return "theaters/theatershow :: theatersFragment";
    }


    @RequestMapping(value = "theater/new", method = RequestMethod.POST)
    @ResponseBody
    public void newTheater(@RequestBody Map<String, String> json, HttpServletResponse res) throws IOException {
        Theater theater = new Theater();
        theater.setSeats(Integer.parseInt(json.get("seats")));
        theater.setCinema(cinemaService.getCinemaById(Integer.parseInt(json.get("cinema"))));
        theaterServiceImpl.saveTheater(theater);
        res.getWriter().println("The theater created successfully");
        res.getWriter().close();
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

    @RequestMapping("theater/delete/{cinemaId}/{id}")
    public String deleteStudent(@PathVariable Integer cinemaId,@PathVariable Integer id) {
        theaterServiceImpl.deleteTheater(cinemaId);
        return "redirect:/cinema/"+cinemaId;
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
