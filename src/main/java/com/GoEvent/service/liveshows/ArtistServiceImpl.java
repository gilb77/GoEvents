package com.GoEvent.service.liveshows;

import com.GoEvent.dao.liveshows.ArtistRepository;
 import com.GoEvent.model.liveshows.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl {


    @Autowired
    private ArtistRepository artistRepository;


    public List<Artist> listAllArtist() {
        return artistRepository.findAll();
    }

    public Artist saveArtist(Artist artist) {
        artistRepository.save(artist);
        return artist;
    }


    public Artist getArtistById(Integer id) {
        return artistRepository.findById(id).orElse(null);
    }


    public String deleteArtist(int id) {
        artistRepository.deleteById(id);
        return "success";
    }


}
