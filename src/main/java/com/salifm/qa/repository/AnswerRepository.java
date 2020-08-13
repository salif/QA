package com.salifm.qa.repository;

import com.salifm.qa.model.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
    List<Answer> findAllByQuestionId(String id);
}
