package com.GoEvent.service.impl;



import com.GoEvent.dao.MovieEventRepository;
import com.GoEvent.model.Movie;
import com.GoEvent.model.MovieEvent;
import com.GoEvent.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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


 public Iterable<MovieEvent> listAllEvents() {
  return movieEventRepository.findAll();
 }


 public Iterable<MovieEvent> listAllEventsByMovie(int id) {
  Iterable<MovieEvent> movieEvents =  movieEventRepository.findAll();
  Iterable<MovieEvent> tempMovieEvents =  movieEventRepository.findAll();
  int index =0;
  for(MovieEvent event: tempMovieEvents){
        if(event.getMovie().getId() != id)
           ((List<MovieEvent>) movieEvents).remove(index);
        index++;
   }
   return movieEvents;
 }

}
