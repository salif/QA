package com.salifm.qa.service;

import com.salifm.qa.model.entity.Question;
import com.salifm.qa.model.view.QuestionViewModel;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    void initQuestions();
    List<QuestionViewModel> getAllQuestions();
    Optional<Question> getQuestion(String id);
}
