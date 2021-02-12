// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.web;

import eu.salif.qa.model.binding.AnswerBindingModel;
import eu.salif.qa.model.view.AnswerViewModel;
import eu.salif.qa.model.view.QuestionViewModel;
import eu.salif.qa.service.AnswerService;
import eu.salif.qa.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
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
    public ModelAndView getQuestionPage(Model model, ModelAndView modelAndView, @PathVariable("id") String id) {
        this.questionService.incViews(id);
        QuestionViewModel question = this.questionService.getQuestion(id);
        List<AnswerViewModel> answers = this.answerService.getAnswers(id);
        modelAndView.addObject("question", question);
        modelAndView.addObject("answers", answers);
        if(!model.containsAttribute("answerBindingModel")){
            model.addAttribute("answerBindingModel", new AnswerBindingModel());
        }
        modelAndView.setViewName("questions/question");
        return modelAndView;
    }

    @PostMapping("/question/{id}/answer")
    public String answer(@Valid
                          @ModelAttribute("answerBindingModel") AnswerBindingModel answerBindingModel,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes,
                          Principal principal, @PathVariable("id") String id) {

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("answerBindingModel", answerBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.answerBindingModel", bindingResult);
            return String.format("redirect:/question/%s/", id);
        }

        this.answerService.postAnswer(id, answerBindingModel.getText(), principal.getName());
        return String.format("redirect:/question/%s/", id);
    }
}
