package com.GoEvent.controller;


import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Log4j
@Controller
public class ManagerController {

    @RequestMapping(value = "/manager")
    public String manager() {
        return "manager";
    }

}
