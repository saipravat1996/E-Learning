package com.silan.service;

import java.util.List;

import com.silan.model.Question;

public interface QuestionService {

	
	public Question saveQuestion(Question question)throws Exception;
	
   public List<Question> listQuestion()throws Exception;
	
	public String deleteQuestion(String lessionId)throws Exception;
	
	public Question getLessionByQuestionId(String QuestionId)throws Exception;
	
	public Question updateQuestion(Question question)throws Exception;
}
