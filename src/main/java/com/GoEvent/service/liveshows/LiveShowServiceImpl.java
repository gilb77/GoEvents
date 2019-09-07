package com.GoEvent.service.liveshows;

import com.GoEvent.dao.liveshows.LiveShowRepository;
import com.GoEvent.model.liveshows.LiveShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveShowServiceImpl {

    @Autowired
    private LiveShowRepository liveShowRepository;


    public List<LiveShow> listAllLiveShow() {
        return liveShowRepository.findAll();
    }

    public LiveShow saveLiveShow(LiveShow liveShow) {
        liveShowRepository.save(liveShow);
        return liveShow;
    }


}
