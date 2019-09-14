package com.GoEvent.service.liveshows;

import com.GoEvent.dao.liveshows.LiveShowRepository;
import com.GoEvent.model.liveshows.LiveShow;
import com.GoEvent.model.movies.Cinema;
import com.GoEvent.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class LiveShowServiceImpl {

    @Autowired
    private LiveShowRepository liveShowRepository;
    @Autowired
    private ArtistServiceImpl artistService;

    public List<LiveShow> listAllLiveShow() {
        return liveShowRepository.findAll();
    }

    public LiveShow saveLiveShow(Map<String, String> json) {
        LiveShow liveShow = new LiveShow();
        liveShow.setAddress(json.get("address"));
        liveShow.setArtist(artistService.getArtistById(Integer.parseInt(json.get("artist"))));
        liveShow.setPlaces(Integer.parseInt(json.get("places")));
        liveShow.setCostStanding(Integer.parseInt(json.get("costStanding")));
        liveShow.setSeats(new int[Integer.parseInt(json.get("numSeating"))]);
        liveShow.setCostSeating(Integer.parseInt(json.get("costSeating")));
        try {
            liveShow.setDate(ParseUtil.parseStringToDate(json.get("date")));
            liveShow.setTime(ParseUtil.parseStringToTime(json.get("time")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        liveShowRepository.save(liveShow);
        return liveShow;
    }



    public LiveShow getLiveShowById(Integer id) {
        return liveShowRepository.findById(id).orElse(null);
    }



}
