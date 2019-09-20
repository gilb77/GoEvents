package com.GoEvent.service.movies.impl;


import com.GoEvent.dao.InvitationRepository;
import com.GoEvent.model.Invitation;
import com.GoEvent.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InvitationServiceImpl {
    @Autowired
    private InvitationRepository invitationRepository;

    public void saveInvitation(int eventId,int quantity,List<Integer> seats,
                               int standsCount,double totalCost){
        Invitation invitation = new Invitation();
        invitation.setEventId(eventId);
        invitation.setQuantity(quantity);
        invitation.setSeats(seats.size());
        invitation.setStandsCount(standsCount);
        invitation.setTotalCost(totalCost);
        invitation.setDate(ParseUtil.getCurrentDate());
        this.saveSeats(invitation,seats);
        invitationRepository.save(invitation);
    }


    private void saveSeats(Invitation invitation,List<Integer> seats){
        int i=0;
        for(int seat: seats){
            invitation.getSeats()[i] = seat;
            i++;
        }
    }
}
