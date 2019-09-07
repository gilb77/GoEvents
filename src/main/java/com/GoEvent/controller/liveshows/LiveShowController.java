package com.GoEvent.controller.liveshows;

import com.GoEvent.model.liveshows.LiveShow;
import com.GoEvent.service.liveshows.LiveShowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/artists")
public class LiveShowController {

    @Autowired
    private LiveShowServiceImpl liveShowService;

    @RequestMapping(value = "/lists", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("liveshows", liveShowService.listAllLiveShow());
        return "liveshows/artists/artists-list";
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newLiveShow(LiveShow liveShow) {
        liveShowService.saveLiveShow(liveShow);
        return "liveshows/artists/artists-list";
    }


    @RequestMapping("/{id}")
    public String showProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("cinema", cinemaService.getCinemaById(id));
        return "cinema/cinemashow";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("cinema", cinemaService.getCinemaById(id));
        return "cinema/cinemaform";
    }
}
