package com.salifm.qa.web;

import com.salifm.qa.model.entity.Question;
import com.salifm.qa.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/question/{id}")
    public ModelAndView question(ModelAndView modelAndView, @PathVariable("id") String id) {
        Question question = this.questionService.getQuestion(id).orElse(new Question());
        modelAndView.addObject("questionTitle", question.getTitle());
        modelAndView.setViewName("question");
        return modelAndView;
    }
}
