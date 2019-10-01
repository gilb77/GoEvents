package com.GoEvent.controller.liveshows;


import com.GoEvent.dao.liveshows.LocationRepository;
import com.GoEvent.model.liveshows.LiveShow;
import com.GoEvent.service.liveshows.ArtistServiceImpl;
import com.GoEvent.service.liveshows.LiveShowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import static com.GoEvent.util.WebUtil.responseForError;

@Controller
@RequestMapping(value = "/liveshows")
public class LiveShowController {

    @Autowired
    private LiveShowServiceImpl liveShowService;
    @Autowired
    private ArtistServiceImpl artistService;

    @Autowired
    private LocationRepository locationRepository;

    @RequestMapping(value = "/lists", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("liveShows", liveShowService.listAllLiveShow());
        return "liveshows/liveshows";
    }

    private boolean checkFieldsEmpty(Map<String, String> json) {
        return Integer.parseInt(json.get("places")) == 0 || Integer.parseInt(json.get("costStanding")) == 0 ||
                Integer.parseInt(json.get("costSeating")) == 0 || Integer.parseInt(json.get("numSeating")) == 0 ||
                json.get("costSeating").isEmpty() || json.get("artist").isEmpty() || json.get("location").isEmpty();
    }

    @RequestMapping(value = "/new/liveShow", method = RequestMethod.POST)
    @ResponseBody
    public String newLiveShow(@RequestBody Map<String, String> json, Model model, HttpServletResponse res) throws IOException, ParseException, InterruptedException {
        if (checkFieldsEmpty(json))
            return responseForError(res, "Fields Empty");
        if (!liveShowService.saveLiveShow(json))
            return responseForError(res, "There is live show on the same time.");
        return "success";
    }

    @RequestMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("liveShow", new LiveShow());
        model.addAttribute("artists", artistService.listAllArtist());
        model.addAttribute("locations", locationRepository.findAll());
        return "liveshows/liveshow-form";
    }

    @RequestMapping("/{id}")
    public String showProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("liveShow", liveShowService.findLiveShowById(id));
        return "liveshows/cinemashow";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("liveShow", liveShowService.findLiveShowById(id));
        model.addAttribute("artists", artistService.listAllArtist());
        model.addAttribute("locations", locationRepository.findAll());
        return "liveshows/liveshow-form";
    }




    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteEvent(@PathVariable("id") int id, Model model) {
        if (!liveShowService.deleteLiveShow(id))
            model.addAttribute("error", "The event was invited by users");
        model.addAttribute("liveShows", liveShowService.listAllLiveShow());
        return "liveshows/liveshows";
    }
}
