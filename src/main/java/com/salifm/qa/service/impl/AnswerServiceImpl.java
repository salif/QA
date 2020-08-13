package com.salifm.qa.service.impl;

import com.salifm.qa.model.view.AnswerViewModel;
import com.salifm.qa.repository.AnswerRepository;
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

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.modelMapper = modelMapper;
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
}
