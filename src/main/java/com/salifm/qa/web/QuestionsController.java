// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.web;

import eu.salif.qa.model.binding.QuestionAskBindingModel;
import eu.salif.qa.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    private final QuestionService questionService;

    @Autowired
    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("")
    public ModelAndView getHomePage(ModelAndView modelAndView) {
        modelAndView.addObject("questions", this.questionService.getAllQuestions());
        modelAndView.setViewName("questions/questions");
        return modelAndView;
    }

    @GetMapping("/new")
    public String getNewPage(Model model) {
        if(!model.containsAttribute("questionAskBindingModel")){
            model.addAttribute("questionAskBindingModel", new QuestionAskBindingModel());
        }
        return "questions/new";
    }
    @PostMapping("/new")
    public String newPage(@Valid
                              @ModelAttribute("questionAskBindingModel") QuestionAskBindingModel questionAskBindingModel,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("questionAskBindingModel", questionAskBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.questionAskBindingModel", bindingResult);
            return "redirect:/questions/new";
        }
        String id = this.questionService.postQuestion(
                questionAskBindingModel.getTitle(), questionAskBindingModel.getText(), principal.getName());


        return String.format("redirect:/question/%s/", id);
    }
}
