package com.GoEvent.controller;


import com.GoEvent.service.impl.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;

    @RequestMapping("/shoppingCart")
    public String shoppingCart(Model model) {
//        model.addAttribute("invites", shoppingCartService.getInvitesInCart());
        model.addAttribute("movieInvites", shoppingCartService.getMovieInvitesInCart());
//        modelAndView.addObject("total", shoppingCartService.getTotal().toString());
        return "shoppingCart";
    }


    @RequestMapping("/shoppingCart/removeProduct/{eventId}")
    public String removeMovieFromCart(@PathVariable("eventId") int eventId, Model model) {
        shoppingCartService.removeMovieInvite(eventId);
        return shoppingCart(model);
    }


//    @GetMapping("/shoppingCart/checkout")
//    public ModelAndView checkout() {
//        try {
//            shoppingCartService.checkout();
//        } catch (NotEnoughProductsInStockException e) {
//            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
//        }
//        return shoppingCart();
//    }
}
