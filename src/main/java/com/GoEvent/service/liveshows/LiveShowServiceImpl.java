package com.GoEvent.service.liveshows;

import com.GoEvent.dao.liveshows.LiveShowRepository;
import com.GoEvent.model.Invitation;
import com.GoEvent.model.liveshows.LiveShow;
import com.GoEvent.service.movies.impl.InvitationServiceImpl;
import com.GoEvent.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LiveShowServiceImpl {

    @Autowired
    private LiveShowRepository liveShowRepository;
    @Autowired
    private ArtistServiceImpl artistService;
    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private InvitationServiceImpl invitationService;

    public static Lock lockRepository = new ReentrantLock();


    public List<LiveShow> listAllLiveShow() {
        return liveShowRepository.findAll();
    }

    public synchronized boolean saveLiveShow(Map<String, String> json) {
        LiveShow liveShow = new LiveShow();
        try {
            if (checkLiveShowNotHappenAtSameTimeMap(json))
                return false;
            parseJsonToLiveShow(liveShow, json);
            saveLiveShow(liveShow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public LiveShow saveLiveShow(LiveShow liveShow) {
        lockRepository.lock();
        try {
            liveShowRepository.save(liveShow);
        } finally {
            lockRepository.unlock();
        }
        return liveShow;
    }

    public LiveShow findLiveShowById(int id) {
        LiveShow liveShow;
        lockRepository.lock();
        try {
            liveShow = liveShowRepository.findById(id).orElse(null);
        } finally {
            lockRepository.unlock();
        }
        return liveShow;
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
        lockRepository.lock();
        List<LiveShow> listLiveShows, liveShowsByArtist;
        try {
            listLiveShows = liveShowRepository.findAll();
            liveShowsByArtist = new ArrayList<>();
            for (LiveShow liveShow : listLiveShows)
                if (checkArtist(liveShow, artistId) && checkLocation(liveShow, location) &&
                        checkDate(liveShow, date) && checkTime(liveShow, time))
                    liveShowsByArtist.add(liveShow);
        } finally {
            lockRepository.unlock();
        }
        return liveShowsByArtist;
    }

    private boolean checkArtist(LiveShow liveShow, int id) {
        return id == -1 || liveShow.getArtist().getId() == id;
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

    public boolean checkInviationOfArtist(int artistId) {
        lockRepository.lock();
        try {

            for (LiveShow liveShow : getLiveShowsByFilter(artistId))
                for (Invitation invitation : invitationService.getAllIvitations())
                    if (invitation.getEventId() == liveShow.getId())
                        return false;
        } finally {
            lockRepository.unlock();
        }
        return true;
    }

    private boolean checkInviation(int eventId) {
        for (Invitation invitation : invitationService.getAllIvitations())
            if (invitation.getEventId() == eventId)
                return true;
        return false;
    }

    public synchronized boolean deleteLiveShow(int id) {
        InvitationServiceImpl.globalLockRepository.lock();
        try {
            if (checkInviation(id)) {
                return false;
            }
            liveShowRepository.deleteById(id);
        } finally {
            InvitationServiceImpl.globalLockRepository.unlock();
        }
        return true;
    }


    private boolean checkLiveShowNotHappenAtSameTimeMap(Map<String, String> json) throws ParseException {
        List<LiveShow> liveShows;
        liveShows = getLiveShowsByFilter(-1,
                Integer.parseInt(json.get("location")),
                ParseUtil.parseStringToDate(json.get("date")),
                ParseUtil.parseStringToTime(json.get("time")));
        return !liveShows.isEmpty();
    }


    private LiveShow parseJsonToLiveShow(LiveShow liveShow, Map<String, String> json) throws ParseException {
        liveShow.setLocation(locationService.getLocationByid(Integer.parseInt(json.get("location"))));
        liveShow.setArtist(artistService.getArtistById(Integer.parseInt(json.get("artist"))));
        liveShow.setStand(Integer.parseInt(json.get("places")));
        liveShow.setCostStanding(Integer.parseInt(json.get("costStanding")));
        liveShow.getSeat().setSeats(Integer.parseInt(json.get("numSeating")));
        liveShow.setCostSeating(Integer.parseInt(json.get("costSeating")));
        liveShow.setDate(ParseUtil.parseStringToDate(json.get("date")));
        liveShow.setTime(ParseUtil.parseStringToTime(json.get("time")));
        return liveShow;
    }
}
