package com.GoEvent.controller.liveshows;


import com.GoEvent.model.liveshows.LiveShow;
import com.GoEvent.service.impl.ShoppingCartServiceImpl;
import com.GoEvent.service.liveshows.LiveShowServiceImpl;
import com.GoEvent.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static com.GoEvent.util.WebUtil.responseForError;


@Controller
@RequestMapping(value = "/invite/liveshow")
public class LiveShowInviteController {

    @Autowired
    private LiveShowServiceImpl liveShowService;

    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;

    @RequestMapping(value = "/location/{artist}")
    public String getLocations(@PathVariable("artist") int artist, Model model, HttpServletResponse res) throws IOException {
        List<LiveShow> liveShows = liveShowService.getLiveShowsByFilter(artist);
        if (liveShows.isEmpty())
            return responseForError(res, "No events");
        model.addAttribute("liveshows", liveShowService.buildLocationsList(liveShows));
        return "liveshows/liveshow-invite-fragments :: locationFragment";
    }

    @RequestMapping(value = "/date", method = RequestMethod.POST)
    public String getDates(@RequestBody Map<String, String> json, ModelMap model, HttpServletResponse res) throws IOException {
        List<LiveShow> liveShows = liveShowService.getLiveShowsByFilter(
                Integer.parseInt(json.get("artist")), Integer.parseInt(json.get("location")));
        if (liveShows.isEmpty())
            return responseForError(res, "No events");
        model.addAttribute("eventsByLocation", liveShowService.buildDatesList(liveShows));
        model.addAttribute("parseUtil", new ParseUtil());
        return "liveshows/liveshow-invite-fragments :: dateFragment";
    }


    @RequestMapping(value = "/time", method = RequestMethod.POST)
    public String getTimes(@RequestBody Map<String, String> json,ModelMap model) throws ParseException {
        model.addAttribute("eventsByDate", liveShowService.getLiveShowsByFilter(
                Integer.parseInt(json.get("artist")), Integer.parseInt(json.get("location")),
                ParseUtil.parseStringToDate(json.get("date"))));
        model.addAttribute("parseUtil", new ParseUtil());
        return "liveshows/liveshow-invite-fragments :: timeFragment";
    }


    @RequestMapping(value = "/seats", method = RequestMethod.POST)
    public String getSeats(@RequestBody Map<String, String> json, ModelMap model, HttpServletResponse res) throws ParseException, IOException {
        List<LiveShow> liveShow = liveShowService.getLiveShowsByFilter(
                Integer.parseInt(json.get("artist")), Integer.parseInt(json.get("location")),
                ParseUtil.parseStringToDate(json.get("date")), ParseUtil.parseStringToTime(json.get("time")));
        if (liveShow.isEmpty())
            return responseForError(res, "No events");
            model.addAttribute("eventsByTime", liveShow.get(0).getSeat().getFreeSeats());
        return "liveshows/liveshow-invite-fragments :: seatFragment";
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void newInvation(@RequestBody Map<String, String> json) throws Exception {
        List<LiveShow> liveShow = liveShowService.getLiveShowsByFilter(
                Integer.parseInt(json.get("artist")),
                Integer.parseInt(json.get("location")), ParseUtil.parseStringToDate(json.get("date")),
                ParseUtil.parseStringToTime(json.get("time")));
        boolean stand = !json.get("iAmStand").equals("0");
        if (liveShow.size() != 1)
            throw new Exception("Duplicate Live show or not exists");
        shoppingCartService.addEventInvites(liveShow.get(0), Integer.parseInt(json.get("seat")), stand);
    }


}
