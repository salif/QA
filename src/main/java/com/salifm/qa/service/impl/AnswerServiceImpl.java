package com.salifm.qa.service.impl;

import com.salifm.qa.model.entity.Answer;
import com.salifm.qa.model.view.AnswerViewModel;
import com.salifm.qa.repository.AnswerRepository;
import com.salifm.qa.repository.QuestionRepository;
import com.salifm.qa.repository.UserRepository;
import com.salifm.qa.service.AnswerService;
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
    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository, UserRepository userRepository, ModelMapper modelMapper, DateTimeFormatter dateTimeFormatter) {
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
        answer.setQuestion(this.questionRepository.findById(questionId).orElseThrow());
        answer.setText(text);
        answer.setAuthor(this.userRepository.findByUsername(authorUsername).orElseThrow());
        answer.setCreatedOn(LocalDateTime.now());
        this.answerRepository.saveAndFlush(answer);
    }
}
