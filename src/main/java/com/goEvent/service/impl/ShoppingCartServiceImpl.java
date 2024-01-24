package com.GoEvent.service.impl;

import com.GoEvent.model.Event;
import com.GoEvent.model.liveshows.LiveShow;
import com.GoEvent.model.movies.MovieEvent;
import com.GoEvent.service.liveshows.LiveShowServiceImpl;
import com.GoEvent.service.movies.impl.InvitationServiceImpl;
import com.GoEvent.service.movies.impl.MovieEventServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Shopping Cart is implemented with a Map, and as a session bean
 *
 * @author Gil
 */
@Data
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl {

    private Map<Integer, CurrentCartEvent> invites = new HashMap<>();

    @Autowired
    private InvitationServiceImpl invitationService;

    @Autowired
    private MovieEventServiceImpl movieEventService;

    @Autowired
    private LiveShowServiceImpl liveShowService;

    @AllArgsConstructor
    @Getter
    @Setter
    class CurrentCartEvent {
        Event event;
        int quantity;
        List<Integer> seats;
        int standsQuantity;
        public void addStandCount() {
            this.standsQuantity++;
        }
    }


    public void addEventInvites(Event event, int seat, boolean stand) throws Exception {
        if (invites.containsKey(getIdMap(event))) {
            if (!stand)
                checkFreePlace(event, seat);
            updateCartEvent(event, seat, stand);
        } else
            newEventCart(event, seat, stand);
    }


    private void checkFreePlace(Event event, int seat) throws Exception {
        for (Integer s : invites.get(getIdMap(event)).seats)
            if (s == seat)
                throw new Exception("The Seat was selected.");

    }

    private void updateCartEvent(Event event, int seat, boolean stand) {
        CurrentCartEvent currentCartEvent = invites.get(getIdMap(event));
        if (stand)
            currentCartEvent.standsQuantity++;
        else
            currentCartEvent.seats.add(seat);
        currentCartEvent.quantity++;
        invites.replace(getIdMap(event), currentCartEvent);
    }


    private void newEventCart(Event event, int seat, boolean stand) {
        CurrentCartEvent currentCartEvent = new CurrentCartEvent(event, 1, new ArrayList<>(), 0);
        if (stand)
            currentCartEvent.addStandCount();
        else
            currentCartEvent.seats.add(seat);
        invites.put(getIdMap(event), currentCartEvent);
    }

    private int getIdMap(Event event) {
        return (event instanceof MovieEvent) ? event.getId() : -1 * event.getId();
    }


    public double getTotal() {
        double sum = 0;
        for (CurrentCartEvent cartEvent : invites.values())
            sum += geTotalEvent(cartEvent);
        return sum;
    }

    public void removeInvite(int id) {
        removeSeats(id);
        invites.remove(id);
    }

    public void removeAllEvents() {
        for (Integer key : invites.keySet())
            removeInvite(key);

    }


    private void removeSeats(int index) {
        for (int seat : invites.get(index).seats)
            invites.get(index).event.getSeat().cancelSeats(seat);
    }

    private void saveEvent(Event event) {
        if (event instanceof MovieEvent)
            movieEventService.saveEvent((MovieEvent) event);
        else if (event instanceof LiveShow)
            liveShowService.saveLiveShow((LiveShow) event);
    }

    public double geTotalEvent(CurrentCartEvent cartEvent) {
        double sum = 0;
        if (cartEvent.event instanceof MovieEvent)
            sum += cartEvent.seats.size() * cartEvent.getEvent().price;
        else if (cartEvent.event instanceof LiveShow) {
            sum += cartEvent.getSeats().size() * ((LiveShow) cartEvent.getEvent()).getCostSeating();
            sum += cartEvent.standsQuantity * ((LiveShow) cartEvent.getEvent()).getCostStanding();
        }
        return sum;
    }

    public boolean checkout(String userName) {
        List<Event> events = new ArrayList<>();
         for (Map.Entry order : invites.entrySet()) {
            CurrentCartEvent cart = (CurrentCartEvent) order.getValue();
            if (!saveSeats(cart) || !saveStands(cart))
                return false;
            invitationService.saveInvitation(cart.getEvent().getId(),
                    cart.getQuantity(), cart.seats, cart.standsQuantity, geTotalEvent(cart), userName);
            events.add(cart.getEvent());
        }
        for (Event event : events)
            removeInvite(getIdMap(event));
        return true;
    }

    private boolean saveSeats(CurrentCartEvent currentCartEvent) {
        Event event = currentCartEvent.event;
        for (Integer seat : currentCartEvent.seats) {
            if (!checkSeat(event, seat))
                return false;
            event.getSeat().saveSeats(seat);
        }
        saveEvent(event);
        return true;
    }

    private boolean saveStands(CurrentCartEvent currentCartEvent) {
        if (currentCartEvent.event instanceof MovieEvent)
            return true;
        LiveShow event = liveShowService.findLiveShowById(currentCartEvent.event.getId());
        if (!event.saveStandPlace(currentCartEvent.standsQuantity))
            return false;
        saveEvent(event);
        return true;
    }

    public boolean checkSeat(Event event, int seat) {
        if (event instanceof MovieEvent)
            return movieEventService.findMovieEventById(event.getId()).getSeat().checkSeat(seat);
        else if (event instanceof LiveShow)
            return liveShowService.findLiveShowById(event.getId()).getSeat().checkSeat(seat);
        return false;
    }

}
