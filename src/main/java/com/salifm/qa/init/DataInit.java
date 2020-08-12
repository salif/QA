package com.salifm.qa.init;


import com.salifm.qa.service.RoleService;
import com.salifm.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public DataInit(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {
        this.roleService.initRoles();
        this.userService.initUsers();
    }
}
