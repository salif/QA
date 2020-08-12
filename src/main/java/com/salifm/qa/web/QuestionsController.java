package com.salifm.qa.web;

import com.salifm.qa.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    private final QuestionService questionService;

    @Autowired
    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("")
    public ModelAndView questions(ModelAndView modelAndView) {
        modelAndView.addObject("questions", this.questionService.getAllQuestions());
        modelAndView.setViewName("questions");
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView newQuestion(ModelAndView modelAndView) {
        return modelAndView;
    }
}
