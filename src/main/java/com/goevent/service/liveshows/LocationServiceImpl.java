package com.GoEvent.service.liveshows;

import com.GoEvent.dao.liveshows.LocationRepository;
import com.GoEvent.model.liveshows.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl {


    @Autowired
    private LocationRepository locationRepository;


    public Location getLocationByid(int id){
        return locationRepository.getOne(id);
    }

}
