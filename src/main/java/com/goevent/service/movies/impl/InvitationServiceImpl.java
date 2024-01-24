package com.GoEvent.service.movies.impl;


import com.GoEvent.dao.InvitationRepository;
import com.GoEvent.dao.UserRepository;
import com.GoEvent.model.Invitation;
import com.GoEvent.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class InvitationServiceImpl {
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private UserRepository userRepository;


    public static Lock globalLockRepository = new ReentrantLock();


    public void saveInvitation(int eventId, int quantity, List<Integer> seats,
                               int standsCount, double totalCost, String userName) {
        globalLockRepository.lock();
        try {
            Invitation invitation = new Invitation();
            invitation.setEventId(eventId);
            invitation.setQuantity(quantity);
            invitation.setSeats(seats.size());
            invitation.setStandsCount(standsCount);
            invitation.setTotalCost(totalCost);
            invitation.setDate(ParseUtil.getCurrentDate());
            invitation.setUser(userRepository.findByUsername(userName).get());
            this.saveSeats(invitation, seats);
            invitationRepository.save(invitation);
        } finally {
            globalLockRepository.unlock();
        }
    }


    public List<Invitation> getAllIvitations() {
        List<Invitation> invitations;
        invitations = invitationRepository.findAll();
        return invitations;
    }

    private void saveSeats(Invitation invitation, List<Integer> seats) {
        int i = 0;
        for (int seat : seats) {
            invitation.getSeats()[i] = seat;
            i++;
        }
    }
}
