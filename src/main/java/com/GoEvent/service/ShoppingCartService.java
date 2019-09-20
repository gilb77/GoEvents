package com.GoEvent.service;

//import com.GoEvent.exception.NotEnoughSeatsInStockException;
import com.GoEvent.model.Invitation;
import com.GoEvent.model.movies.MovieInvite;

import java.util.List;

public interface ShoppingCartService {

    void addInvite(Invitation invites);

    void removeInvite(Invitation invites);

    List<Invitation> getInvitesInCart();
    List<MovieInvite> getMovieInvitesInCart();

//    void checkout() throws NotEnoughSeatsInStockException;
//
//    BigDecimal getTotal();

}
