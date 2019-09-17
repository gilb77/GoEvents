package com.GoEvent.controller.liveshows;


import com.GoEvent.model.liveshows.LiveShow;
import com.GoEvent.service.liveshows.ArtistServiceImpl;
import com.GoEvent.service.liveshows.LiveShowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/liveshows")
public class LiveShowController {

    @Autowired
    private LiveShowServiceImpl liveShowService;
    @Autowired
    private ArtistServiceImpl artistService;

    @RequestMapping(value = "/lists", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("liveShows", liveShowService.listAllLiveShow());
        return "liveshows/liveshows";
    }




    @RequestMapping(value = "/new/liveShow", method = RequestMethod.POST)
    @ResponseBody
    public String newLiveShow(@RequestBody Map<String, String> json) {
        liveShowService.saveLiveShow(json);
        return "redirect:liveshows/liveshows";
    }

    @RequestMapping("/new")
    public String newProduct(Model model) {
        LiveShow liveShow = new LiveShow();
        model.addAttribute("liveShow", liveShow);
        model.addAttribute("artists", artistService.listAllArtist());
        return "liveshows/liveshow-form";
    }

    @RequestMapping("/{id}")
    public String showProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("liveShow", liveShowService.getLiveShowById(id));
        return "liveshows/cinemashow";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("liveShow", liveShowService.getLiveShowById(id));
        model.addAttribute("artists", artistService.listAllArtist());
        return "liveshows/liveshow-form";
    }
}