package com.GoEvent.service.impl;


import com.GoEvent.dao.MovieInviteRepository;
import com.GoEvent.dao.MovieRepository;
import com.GoEvent.model.MovieEvent;
import com.GoEvent.model.MovieInvite;
import com.GoEvent.service.MovieInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MovieInviteServiceImpl implements MovieInviteService {

  @Autowired
  private MovieInviteRepository movieInviteRepository;

  @Autowired
  private MovieEventServiceImpl movieEventService;

    public void newInvite(int movie, String city, int cinema, Date date, Date time,String seat) throws Exception {
        MovieInvite movieInvite = new MovieInvite();
        movieInvite.setMovieEvent(movieEventService.getMovieEvent( movie,city,cinema,date,time));
        movieInvite.setSeat(Integer.parseInt(seat));
        movieInviteRepository.save(movieInvite);
    }
}
