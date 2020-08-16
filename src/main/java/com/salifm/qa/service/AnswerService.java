// SPDX-FileCopyrightText: 2020 Salif Mehmed <salifm@salifm.com>
// SPDX-License-Identifier: MIT

package com.salifm.qa.service;

import com.salifm.qa.model.view.AnswerViewModel;

import java.util.List;

public interface AnswerService {
    List<AnswerViewModel> getAnswers(String questionId);
    void postAnswer(String questionId, String text, String authorUsername);
}
