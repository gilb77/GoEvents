package com.GoEvent.service;

//import com.GoEvent.exception.NotEnoughSeatsInStockException;
import com.GoEvent.model.Invite;
import com.GoEvent.model.movies.MovieInvite;

import java.util.List;

public interface ShoppingCartService {

    void addInvite(Invite invites);

    void removeInvite(Invite invites);

    List<Invite> getInvitesInCart();
    List<MovieInvite> getMovieInvitesInCart();

//    void checkout() throws NotEnoughSeatsInStockException;
//
//    BigDecimal getTotal();

}
