// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.service;

import eu.salif.qa.model.view.AnswerViewModel;

import java.util.List;

public interface AnswerService {
    List<AnswerViewModel> getAnswers(String questionId);
    void postAnswer(String questionId, String text, String authorUsername);
}
