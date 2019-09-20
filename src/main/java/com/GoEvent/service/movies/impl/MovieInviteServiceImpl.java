package com.GoEvent.service.movies.impl;


import com.GoEvent.dao.movies.MovieInviteRepository;
import com.GoEvent.model.Event;
import com.GoEvent.model.movies.MovieEvent;
import com.GoEvent.service.impl.ShoppingCartServiceImpl;
import com.GoEvent.service.movies.MovieInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MovieInviteServiceImpl implements MovieInviteService {



  @Autowired
  private MovieEventServiceImpl movieEventService;



    public void newInvite(int movie, String city, int cinema, Date date, Date time,String seat) throws Exception {


    }
}
