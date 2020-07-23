package com.silan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silan.model.Question;

public interface QuestionRepository extends JpaRepository<Question, String> {

}
