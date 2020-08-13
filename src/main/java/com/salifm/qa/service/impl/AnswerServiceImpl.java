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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, ModelMapper modelMapper, QuestionRepository questionRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.modelMapper = modelMapper;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<AnswerViewModel> getAnswers(String questionId) {
        return this.answerRepository.findAllByQuestionId(questionId).stream()
                .map(answer -> {
                    AnswerViewModel answerViewModel = this.modelMapper.map(answer, AnswerViewModel.class);
                    answerViewModel.setAuthorName(answer.getAuthor().getUsername());
                    answerViewModel.setAuthorId(answer.getAuthor().getId());
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
        this.answerRepository.saveAndFlush(answer);
    }
}
