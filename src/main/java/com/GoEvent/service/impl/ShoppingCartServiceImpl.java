package com.GoEvent.service.impl;

import com.GoEvent.dao.movies.MovieInviteRepository;
import com.GoEvent.model.movies.MovieEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

/**
 * Shopping Cart is implemented with a Map, and as a session bean
 *
 * @author Gil
 */

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl {

    @Autowired
    private MovieInviteRepository movieInviteRepository;

//    private Map<Invite> invites = new ArrayList<>();
    private Map<Integer, CurrentCartMovies> movieInvites = new HashMap<>();

    @AllArgsConstructor
    @Getter
    @Setter
    class CurrentCartMovies {
        MovieEvent MovieEvent;
        int quantity;
        List<Integer> seats;
    }

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param event
     */
    public void addMovieInvites(MovieEvent event, int seat) throws Exception {
        if (movieInvites.containsKey(event.getId())) {
            CurrentCartMovies currentCartMovies = movieInvites.get(event.getId());
            for (Integer s : currentCartMovies.seats)
                if (s == seat)
                    throw new Exception();
            currentCartMovies.quantity++;
            currentCartMovies.seats.add(seat);
            movieInvites.replace(event.getId(), currentCartMovies);
        } else {
            List<Integer> list = new ArrayList<>();
            list.add(seat);
            movieInvites.put(event.getId(), new CurrentCartMovies(event,1,list));

        }

    }


    public Map<Integer, CurrentCartMovies> getMovieInvitesInCart() {
        return Collections.unmodifiableMap(movieInvites);
    }

//    @Override
    public void removeMovieInvite(int id) {
        movieInvites.remove(id);
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
//    @Override
//    public BigDecimal getTotal() {
//        return invites.entrySet().stream()
//                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
//                .reduce(BigDecimal::add)
//                .orElse(BigDecimal.ZERO);
//    }
}
