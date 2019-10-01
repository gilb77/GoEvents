package com.GoEvent.controller;


import com.GoEvent.service.impl.ShoppingCartServiceImpl;
import com.GoEvent.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;

    @RequestMapping("/shoppingCart")
    public String shoppingCart(Model model) {
        model.addAttribute("invites", shoppingCartService.getInvites());
        model.addAttribute("total", shoppingCartService.getTotal());
        model.addAttribute("parseUtil", new ParseUtil());
        return "shoppingCart";
    }


    @RequestMapping("/shoppingCart/removeProduct/{eventId}")
    public String removeEventFromCart(@PathVariable("eventId") int eventId, Model model) {
        shoppingCartService.removeInvite(eventId);
        return shoppingCart(model);
    }

    @GetMapping("/shoppingCart/checkout")
    public String checkout(Model model,Authentication authentication) {
        if(!shoppingCartService.checkout(authentication.getName())) {
            model.addAttribute("error", "The places you want are save");
            shoppingCartService.removeAllEvents();
            return "shoppingCart";
        }
        return shoppingCart(model);
    }
}
