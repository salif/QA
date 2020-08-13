package com.salifm.qa.web;

import com.salifm.qa.model.view.AnswerViewModel;
import com.salifm.qa.model.view.QuestionViewModel;
import com.salifm.qa.service.AnswerService;
import com.salifm.qa.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping("/question/{id}")
    public ModelAndView getQuestionPage(ModelAndView modelAndView, @PathVariable("id") String id) {
        QuestionViewModel question = this.questionService.getQuestion(id);
        List<AnswerViewModel> answers = this.answerService.getAnswers(id);
        question.setAnswersCount(String.valueOf(answers.size()));
        modelAndView.addObject("question", question);
        modelAndView.addObject("answers", answers);
        modelAndView.setViewName("questions/question");
        return modelAndView;
    }
}
