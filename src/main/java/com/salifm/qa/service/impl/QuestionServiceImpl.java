// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.service.impl;

import eu.salif.qa.constants.Questions;
import eu.salif.qa.constants.Users;
import eu.salif.qa.model.entity.Answer;
import eu.salif.qa.model.entity.Question;
import eu.salif.qa.model.entity.User;
import eu.salif.qa.model.view.QuestionPreviewViewModel;
import eu.salif.qa.model.view.QuestionViewModel;
import eu.salif.qa.repository.AnswerRepository;
import eu.salif.qa.repository.QuestionRepository;
import eu.salif.qa.repository.UserRepository;
import eu.salif.qa.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final DateTimeFormatter dateTimeFormatter;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository,
                               UserRepository userRepository, ModelMapper modelMapper,
                               DateTimeFormatter dateTimeFormatter) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public void initQuestions() {
        if(this.questionRepository.count() == 0 && this.answerRepository.count() == 0) {
            User admin = this.userRepository.findByUsername(Users.ADMIN_USERNAME).orElseThrow();
            Question question = new Question();
            question.set(admin, Questions.QUESTION_TITLE, Questions.QUESTION_TEXT, LocalDateTime.now());
            Answer answer = new Answer();
            answer.set(admin, question, Questions.ANSWER, LocalDateTime.now());
            this.questionRepository.saveAndFlush(question);
            this.answerRepository.saveAndFlush(answer);
        }
    }

    @Override
    public List<QuestionPreviewViewModel> getAllQuestions() {
        return this.questionRepository.findAllByOrderByCreatedOn().stream()
                .map(question -> this.modelMapper.map(question, QuestionPreviewViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public QuestionViewModel getQuestion(String id) {
        Question question = this.questionRepository.findById(id).orElseThrow();
        QuestionViewModel questionViewModel = this.modelMapper.map(question, QuestionViewModel.class);
        questionViewModel.setAuthorName(question.getAuthor().getUsername());
        questionViewModel.setAuthorId(question.getAuthor().getId());
        questionViewModel.setCreatedOn(question.getCreatedOn().format(dateTimeFormatter));
        return questionViewModel;
    }

    @Override
    public String postQuestion(String title, String text, String authorUsername) {
        Question question = new Question();
        question.setTitle(title);
        question.setText(text);
        question.setAuthor(this.userRepository.findByUsername(authorUsername).orElseThrow());
        question.setCreatedOn(LocalDateTime.now());
        question.setViews(0);
        this.questionRepository.saveAndFlush(question);
        return String.valueOf(question.getId());
    }

    @Override
    public void incViews(String id) {
        Question question = this.questionRepository.findById(id).orElseThrow();
        question.setViews(question.getViews() + 1);
        this.questionRepository.saveAndFlush(question);
    }
}
