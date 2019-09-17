package com.GoEvent.service.liveshows;

import com.GoEvent.dao.liveshows.LiveShowRepository;
import com.GoEvent.model.liveshows.LiveShow;
import com.GoEvent.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
        liveShow.setSeats(Integer.parseInt(json.get("numSeating")) );
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


    public List<LiveShow> getLiveShowsByFilter(int artistId){
        return getLiveShowsByFilter(artistId,null);
    }

    public List<LiveShow> getLiveShowsByFilter(int artistId,String location){
        return getLiveShowsByFilter(artistId,location,null);
    }

    public List<LiveShow> getLiveShowsByFilter(int artistId,String location,Date date){
        return getLiveShowsByFilter(artistId,location,date,null);
    }

    public List<LiveShow> getLiveShowsByFilter(int artistId, String location, Date date,Date time) {
        List<LiveShow> listLiveShows = liveShowRepository.findAll();
        List<LiveShow> liveShowsByArtist = new ArrayList<>();
        for (LiveShow liveShow : listLiveShows)
            if (liveShow.getArtist().getId() == artistId && checkLocation(liveShow,location) &&
                    checkDate(liveShow,date)&&checkTime(liveShow,time))
                liveShowsByArtist.add(liveShow);
        return liveShowsByArtist;
    }

    public LiveShow getLiveShowById(Integer id) {
        return liveShowRepository.findById(id).orElse(null);
    }

    private boolean checkLocation(LiveShow liveShow, String location){
        return location == null ||(liveShow.getAddress().equals(location));
    }

    private boolean checkDate(LiveShow liveShow, Date date){
        return date == null ||(liveShow.getDate().compareTo(date)==0);
    }

    private boolean checkTime(LiveShow liveShow, Date date){
        return date == null ||(liveShow.getTime().compareTo(date)==0);
    }


}
