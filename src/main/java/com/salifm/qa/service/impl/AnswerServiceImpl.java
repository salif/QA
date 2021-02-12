// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.service.impl;

import eu.salif.qa.model.entity.Answer;
import eu.salif.qa.model.entity.Question;
import eu.salif.qa.model.view.AnswerViewModel;
import eu.salif.qa.repository.AnswerRepository;
import eu.salif.qa.repository.QuestionRepository;
import eu.salif.qa.repository.UserRepository;
import eu.salif.qa.service.AnswerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final DateTimeFormatter dateTimeFormatter;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository,
                             UserRepository userRepository, ModelMapper modelMapper,
                             DateTimeFormatter dateTimeFormatter) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public List<AnswerViewModel> getAnswers(String questionId) {
        return this.answerRepository.findAllByQuestionIdOrderByCreatedOn(questionId).stream()
                .map(answer -> {
                    AnswerViewModel answerViewModel = this.modelMapper.map(answer, AnswerViewModel.class);
                    answerViewModel.setAuthorName(answer.getAuthor().getUsername());
                    answerViewModel.setAuthorId(answer.getAuthor().getId());
                    answerViewModel.setCreatedOn(answer.getCreatedOn().format(dateTimeFormatter));
                    return answerViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void postAnswer(String questionId, String text, String authorUsername) {
        Answer answer = new Answer();
        Question question = this.questionRepository.findById(questionId).orElseThrow();
        answer.set(this.userRepository.findByUsername(authorUsername).orElseThrow(),
                question, text, LocalDateTime.now());
        question.setAnswerCount(question.getAnswerCount() + 1);
        this.answerRepository.saveAndFlush(answer);
    }
}
