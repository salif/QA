package com.salifm.qa.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionsController {
    @GetMapping("/questions")
    public String questions(Model model) {
        return "questions";
    }
}
