package com.silan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silan.generator.LoginIdGenerator;
import com.silan.model.Question;
import com.silan.repo.QuestionRepository;
import com.silan.service.QuestionService;

@Service 
public class QuestionServiveImpl implements QuestionService {

	
	@Autowired
	private QuestionRepository repo;
	
	@Override
	public Question saveQuestion(Question question) throws Exception {
		question.setQuestionId(LoginIdGenerator.loginID());
		return repo.save(question);
	}

	@Override
	public List<Question> listQuestion() throws Exception {
		
		return repo.findAll();
	}

	@Override
	public String deleteQuestion(String queStringId) throws Exception {
	
		if(repo.existsById(queStringId)) {
			repo.deleteById(queStringId);
			return "success";
		}
		return "fail";
	}

	@Override
	public Question getLessionByQuestionId(String QuestionId) throws Exception {
		
		return repo.findById(QuestionId).get();
	}

	@Override
	public Question updateQuestion(Question question) throws Exception {
		
		return repo.save(question);
	}

}
