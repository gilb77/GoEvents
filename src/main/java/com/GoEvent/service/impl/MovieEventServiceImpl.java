package com.GoEvent.service.impl;



import com.GoEvent.dao.MovieEventRepository;
import com.GoEvent.model.MovieEvent;
import com.GoEvent.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class MovieEventServiceImpl implements EventService {

 private MovieEventRepository movieEventRepository;


 @Autowired
 public void setMovieEventRepository(MovieEventRepository movieEventRepository) {
  this.movieEventRepository = movieEventRepository;
 }

 public void saveEvent(MovieEvent movieEvent){
  this.movieEventRepository.save(movieEvent);
 }

}
