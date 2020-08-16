// SPDX-FileCopyrightText: 2020 Salif Mehmed <salifm@salifm.com>
// SPDX-License-Identifier: MIT

package com.salifm.qa.web;

import com.salifm.qa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RolesController {

    private final RoleService roleService;

    @Autowired
    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ModelAndView getRolesPage(ModelAndView modelAndView) {
        modelAndView.addObject("roles", this.roleService.getAllRoles());
        modelAndView.setViewName("roles/roles");
        return modelAndView;
    }

    @GetMapping("/role/{id}")
    public ModelAndView getRolePage(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("role", this.roleService.getRole(id));
        modelAndView.setViewName("roles/role");
        return modelAndView;
    }
}
