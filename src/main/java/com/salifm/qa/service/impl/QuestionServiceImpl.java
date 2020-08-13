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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initQuestions() {
        if(this.questionRepository.count() == 0 && this.answerRepository.count() == 0) {
            Question question = new Question();
            User admin = this.userRepository.findByUsername(Users.ADMIN_USERNAME).orElse(null);
            question.setAuthor(admin);
            question.setTitle(Questions.QUESTION_TITLE);
            question.setText(Questions.QUESTION_TEXT);
            Answer answer = new Answer();
            answer.setAuthor(admin);
            answer.setQuestion(question);
            answer.setText(Questions.ANSWER);
            this.questionRepository.saveAndFlush(question);
            this.answerRepository.saveAndFlush(answer);
        }
    }

    @Override
    public List<QuestionPreviewViewModel> getAllQuestions() {
        return this.questionRepository.findAll().stream()
                .map(question -> this.modelMapper.map(question, QuestionPreviewViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public QuestionViewModel getQuestion(String id) {
        Question question = this.questionRepository.findById(id).orElse(new Question());
        QuestionViewModel questionViewModel = this.modelMapper.map(question, QuestionViewModel.class);
        questionViewModel.setAuthorName(question.getAuthor().getUsername());
        questionViewModel.setAuthorId(question.getAuthor().getId());
        return questionViewModel;
    }

    @Override
    public String postQuestion(String title, String text, String authorUsername) {
        Question question = new Question();
        question.setTitle(title);
        question.setText(text);
        question.setAuthor(this.userRepository.findByUsername(authorUsername).orElse(new User()));
        this.questionRepository.saveAndFlush(question);
        return String.valueOf(question.getId());
    }
}
