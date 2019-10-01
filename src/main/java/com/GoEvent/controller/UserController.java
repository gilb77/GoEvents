package com.GoEvent.controller;


import com.GoEvent.dao.UserRepository;
import com.GoEvent.model.User;
import com.GoEvent.service.impl.UserServiceImp;
import com.GoEvent.service.movies.impl.InvitationServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Log4j
@Controller
public class UserController {

    @Autowired
    InvitationServiceImpl invitationService;

    @Autowired
    UserServiceImp userServiceImp;


    @RequestMapping(value = "/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users",userServiceImp.findAllUsers());
        return "users/usersTable";
    }

    @RequestMapping(value = "/users/delete/{id}")
    public String deleteUser(@PathVariable Long id,Model model){
        userServiceImp.deleteById(id);
        return "redirect:/users/";
    }


    @RequestMapping(value = "/users/detail")
    public String editUser(Model model,Authentication authentication){
        model.addAttribute("user",userServiceImp.findByUsername(authentication.getName()).get());
        return "users/user-detail";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String updateUser(User user, Model model,Authentication authentication)  {
        if (user.getName().isEmpty() || user.getEmail().isEmpty() || user.getLastName().isEmpty()) {
            model.addAttribute("error", "One of the field is empty.");
            return "users/user-detail";
        }

        User user1 = userServiceImp.findByEmail(user.getEmail()).get();
        if(user1 != null && !user1.getUsername().equals(authentication.getName())){
            model.addAttribute("error", "User email already exists.");
            return "users/user-detail";
        }
        User tempUser =userServiceImp.findByUsername(authentication.getName()).get();
        tempUser.setName(user.getName());
        tempUser.setEmail(user.getEmail());
        tempUser.setLastName(user.getLastName());
        userServiceImp.saveOnlyUser(tempUser);
        model.addAttribute("successMessage", "User has been update successfully");
        return "users/user-detail";
    }

}
