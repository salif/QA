// SPDX-FileCopyrightText: 2020 Salif Mehmed <salifm@salifm.com>
// SPDX-License-Identifier: MIT

package com.salifm.qa.web;

import com.salifm.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getMyProfilePage(Principal principal) {
        return String.format("redirect:/profile/%s/", userService.getUserId(principal.getName()));
    }

    @GetMapping("/profile/{id}")
    public ModelAndView getProfilePage(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("user", this.userService.getProfile(id));
        modelAndView.setViewName("profile");
        return modelAndView;
    }
}
