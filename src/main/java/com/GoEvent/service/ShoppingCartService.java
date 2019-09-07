package com.GoEvent.service;

import com.GoEvent.exception.NotEnoughSeatsInStockException;
import com.GoEvent.model.Invite;
import com.GoEvent.model.MovieInvite;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShoppingCartService {

    void addInvite(Invite invites);

    void removeInvite(Invite invites);

    List<Invite> getInvitesInCart();
    List<MovieInvite> getMovieInvitesInCart();

//    void checkout() throws NotEnoughSeatsInStockException;
//
//    BigDecimal getTotal();

}
