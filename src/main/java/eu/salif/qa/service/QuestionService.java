// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.service;

import eu.salif.qa.model.view.QuestionPreviewViewModel;
import eu.salif.qa.model.view.QuestionViewModel;

import java.util.List;

public interface QuestionService {
    void initQuestions();
    List<QuestionPreviewViewModel> getAllQuestions();
    QuestionViewModel getQuestion(String id);
    String postQuestion(String title, String text, String authorUsername);

    void incViews(String id);
}
