package com.GoEvent.controller;


import com.GoEvent.dao.UserRepository;
import com.GoEvent.service.movies.impl.InvitationServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Log4j
@Controller
public class ManagerController {

    @Autowired
    InvitationServiceImpl invitationService;


    @RequestMapping(value = "/manager")
    public String manager() {
        return "manager";
    }

    @RequestMapping(value = "/invitations")
    public String invitation(Model model) {
        model.addAttribute("invitations",invitationService.getAllIvitations());
        return "invitationsTable";
    }


}
