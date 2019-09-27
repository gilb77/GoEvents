package com.GoEvent.service.liveshows;

import com.GoEvent.dao.liveshows.LiveShowRepository;
import com.GoEvent.model.liveshows.LiveShow;
import com.GoEvent.model.movies.MovieEvent;
import com.GoEvent.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class LiveShowServiceImpl {

    @Autowired
    private LiveShowRepository liveShowRepository;
    @Autowired
    private ArtistServiceImpl artistService;
    @Autowired
    private LocationServiceImpl locationService;

    public List<LiveShow> listAllLiveShow() {
        return liveShowRepository.findAll();
    }

    public LiveShow saveLiveShow(Map<String, String> json) {
        LiveShow liveShow = new LiveShow();
        liveShow.setLocation(locationService.getLocationByid(Integer.parseInt(json.get("location"))));
        liveShow.setArtist(artistService.getArtistById(Integer.parseInt(json.get("artist"))));
        liveShow.setStand(Integer.parseInt(json.get("places")));
        liveShow.setCostStanding(Integer.parseInt(json.get("costStanding")));
        liveShow.getSeat().setSeats(Integer.parseInt(json.get("numSeating")));
        liveShow.setCostSeating(Integer.parseInt(json.get("costSeating")));
        try {
            liveShow.setDate(ParseUtil.parseStringToDate(json.get("date")));
            liveShow.setTime(ParseUtil.parseStringToTime(json.get("time")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
      return saveLiveShow(liveShow);
    }

    public LiveShow saveLiveShow(LiveShow liveShow){
        liveShowRepository.save(liveShow);
        return liveShow;
    }

    public LiveShow findLiveShowById(int id) {
        return liveShowRepository.findById(id).get();
    }


    public List<LiveShow> getLiveShowsByFilter(int artistId) {
        return getLiveShowsByFilter(artistId, -1);
    }

    public List<LiveShow> getLiveShowsByFilter(int artistId, int location) {
        return getLiveShowsByFilter(artistId, location, null);
    }

    public List<LiveShow> getLiveShowsByFilter(int artistId, int location, Date date) {
        return getLiveShowsByFilter(artistId, location, date, null);
    }

    public List<LiveShow> getLiveShowsByFilter(int artistId, int location, Date date, Date time) {
        List<LiveShow> listLiveShows = liveShowRepository.findAll();
        List<LiveShow> liveShowsByArtist = new ArrayList<>();
        for (LiveShow liveShow : listLiveShows)
            if (liveShow.getArtist().getId() == artistId && checkLocation(liveShow, location) &&
                    checkDate(liveShow, date) && checkTime(liveShow, time))
                liveShowsByArtist.add(liveShow);
        return liveShowsByArtist;
    }

    public LiveShow getLiveShowById(Integer id) {
        return liveShowRepository.findById(id).orElse(null);
    }

    private boolean checkLocation(LiveShow liveShow, int location) {
        return location == -1 || (liveShow.getLocation().getId() == location);
    }

    private boolean checkDate(LiveShow liveShow, Date date) {
        return date == null || (liveShow.getDate().compareTo(date) == 0);
    }

    private boolean checkTime(LiveShow liveShow, Date date) {
        return date == null || (liveShow.getTime().compareTo(date) == 0);
    }


    public List<LiveShow> buildLocationsList(List<LiveShow> liveShows) {
        List<LiveShow> tempMovieEvents = new ArrayList<>();
        List<String> locations = new ArrayList<>();

        for (LiveShow event : liveShows)
            locations.add(event.getLocation().getLocation());
        locations = new ArrayList<>(new HashSet<>(locations));
        for (LiveShow event : liveShows)
            if (locations.contains(event.getLocation().getLocation())) {
                tempMovieEvents.add(event);
                locations.remove(event.getLocation().getLocation());
            }
        return tempMovieEvents;
    }


    public List<LiveShow> buildDatesList(List<LiveShow> liveShows) {
        List<LiveShow> tempMovieEvents = new ArrayList<>();
        List<Date> dates = new ArrayList<>();

        for (LiveShow event : liveShows)
            dates.add(event.getDate());
        dates = new ArrayList<>(new HashSet<>(dates));
        for (LiveShow event : liveShows)
            if (dates.contains(event.getDate())) {
                tempMovieEvents.add(event);
                dates.remove(event.getDate());
            }
        return tempMovieEvents;
    }

}
