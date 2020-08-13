package com.salifm.qa.service.impl;

import com.salifm.qa.constants.Questions;
import com.salifm.qa.constants.Users;
import com.salifm.qa.model.entity.Answer;
import com.salifm.qa.model.entity.Question;
import com.salifm.qa.model.entity.User;
import com.salifm.qa.model.view.QuestionPreviewViewModel;
import com.salifm.qa.model.view.QuestionViewModel;
import com.salifm.qa.repository.AnswerRepository;
import com.salifm.qa.repository.QuestionRepository;
import com.salifm.qa.repository.UserRepository;
import com.salifm.qa.service.QuestionService;
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
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository, UserRepository userRepository, ModelMapper modelMapper, DateTimeFormatter dateTimeFormatter) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public void initQuestions() {
        if(this.questionRepository.count() == 0 && this.answerRepository.count() == 0) {
            Question question = new Question();
            User admin = this.userRepository.findByUsername(Users.ADMIN_USERNAME).orElse(null);
            question.setAuthor(admin);
            question.setTitle(Questions.QUESTION_TITLE);
            question.setText(Questions.QUESTION_TEXT);
            question.setCreatedOn(LocalDateTime.now());
            question.setViews(0);
            Answer answer = new Answer();
            answer.setAuthor(admin);
            answer.setQuestion(question);
            answer.setText(Questions.ANSWER);
            answer.setCreatedOn(LocalDateTime.now());
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
        Question question = this.questionRepository.findById(id).orElse(new Question());
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
