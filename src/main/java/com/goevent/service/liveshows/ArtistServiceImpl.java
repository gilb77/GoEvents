package com.GoEvent.service.liveshows;

import com.GoEvent.dao.liveshows.ArtistRepository;
import com.GoEvent.model.liveshows.Artist;
import com.GoEvent.model.liveshows.LiveShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ArtistServiceImpl {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private LiveShowServiceImpl liveShowService;

    private static Lock lockRepository = new ReentrantLock();

    public List<Artist> listAllArtist() {
        lockRepository.lock();
        List<Artist> artists;
        try {
            artists = artistRepository.findAll();
        } finally {
            lockRepository.unlock();
        }
        return artists;
    }

    public boolean saveArtist(Artist artist) {
        lockRepository.lock();
        try {
            if (artistNameExists(artist.getName()) && artist.getId() == 0)
                return false;

            artistRepository.save(artist);
        } finally {
            lockRepository.unlock();
        }
        return true;
    }

    public Artist getArtistById(Integer id) {
        lockRepository.lock();
        Artist artist;
        try {
            artist = artistRepository.findById(id).orElse(null);
        } finally {
            lockRepository.unlock();
        }
        return artist;
    }


    public synchronized boolean deleteArtist(int id) {
        lockRepository.lock();
        try {
            if (!liveShowService.checkInviationOfArtist(id))
                return false;
            for (LiveShow liveShow : liveShowService.getLiveShowsByFilter(id))
                if (!liveShowService.deleteLiveShow(liveShow.getId()))
                    return false;
            artistRepository.deleteById(id);
        } finally {
            lockRepository.unlock();
        }
        return true;
    }


    private boolean artistNameExists(String name) {
        List<Artist> artists = artistRepository.findAll();
        for (Artist artist : artists)
            if (artist.getName().equals(name))
                return true;
        return false;
    }


}
