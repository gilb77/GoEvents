package com.GoEvent.service.impl;

import com.GoEvent.model.Event;
import com.GoEvent.model.liveshows.LiveShow;
import com.GoEvent.model.movies.MovieEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl {

    private Map<Integer, CurrentCartEvent> invites = new HashMap<>();

    @AllArgsConstructor
    @Getter
    @Setter
    class CurrentCartEvent {
        Event event;
        int quantity;
        List<Integer> seats;
        int standsCount;
    }


    public void addEventInvites(Event event, int seat, boolean stand) throws Exception {
        if (invites.containsKey(event.getId())) {
            if (!stand)
                checkFreePlace(event, seat);
            updateCartEvent(event, seat, stand);
        } else
            newEventCart(event, seat, stand);
    }


    private void checkFreePlace(Event event, int seat) throws Exception {
        for (Integer s : invites.get(event.getId()).seats)
            if (s == seat)
                throw new Exception("The Seat was selected.");
    }

    private void updateCartEvent(Event event, int seat, boolean stand) {
        CurrentCartEvent currentCartEvent = invites.get(event.getId());
        if (stand)
            currentCartEvent.standsCount++;
        else {
            currentCartEvent.quantity++;
            currentCartEvent.seats.add(seat);
        }
        invites.replace(event.getId(), currentCartEvent);
    }

    private void newEventCart(Event event, int seat, boolean stand) {
        List<Integer> seats = new ArrayList<>();
        int standCount = 0;
        if (stand)
            standCount++;
        else
            seats.add(seat);
        invites.put(event.getId(), new CurrentCartEvent(event, 1, seats, standCount));
    }


    public double getTotal() {
        double sum = 0;
        for (CurrentCartEvent cartEvent : invites.values()) {
            if (cartEvent.event instanceof MovieEvent)
                sum += cartEvent.seats.size() * cartEvent.getEvent().price;
            else if (cartEvent.event instanceof LiveShow) {
                sum += cartEvent.seats.size() * ((LiveShow) cartEvent.getEvent()).getCostSeating();
                sum += cartEvent.standsCount * ((LiveShow) cartEvent.getEvent()).getCostStanding();
            }
        }
        return sum;
    }

    public void removeInvite(int id) {
        invites.remove(id);
    }
//
//    /**
//     * @return unmodifiable copy of the map
//     */
//    @Override
//    public List<Invite> getInvitesInCart() {
//        return this.invites;
//    }
//

//    /**
//     * @return unmodifiable copy of the map
//     */
//    @Override
//    public List<Invite> getInvitesMoviesInCart() {
//        return  this.invites ;
//    }

//    /**
//     * Checkout will rollback if there is not enough of some product in stock
//     *
//     * @throws NotEnoughSeatsInStockException
//     */
//    @Override
//    public void checkout() {
//        Invite invite;
//        for (Map.Entry<Invite, Integer> entry : invites.entrySet()) {
//            // Refresh quantity for every product before checking
//            invite = productRepository.findOne(entry.getKey().getId());
//            if (invite.getQuantity() < entry.getValue())
//                throw new NotEnoughmovieInvitesInStockException(product);
//            entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
//        }
//        productRepository.save(movieInvites.keySet());
//        productRepository.flush();
//        movieInvites.clear();
//MovieInvite movieInvite = new MovieInvite();
//        movieInvite.setMovieEvent();
//        movieInvite.setSeat();
//    }
//

}
