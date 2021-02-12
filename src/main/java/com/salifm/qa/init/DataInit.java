// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.init;


import eu.salif.qa.service.QuestionService;
import eu.salif.qa.service.RoleService;
import eu.salif.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    private final RoleService roleService;
    private final UserService userService;
    private final QuestionService questionService;

    @Autowired
    public DataInit(RoleService roleService, UserService userService, QuestionService questionService) {
        this.roleService = roleService;
        this.userService = userService;
        this.questionService = questionService;
    }


    @Override
    public void run(String... args) throws Exception {
        this.roleService.initRoles();
        this.userService.initUsers();
        this.questionService.initQuestions();
    }
}
