package com.silan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silan.generator.LoginIdGenerator;
import com.silan.model.Lession;
import com.silan.repo.LessionRepository;
import com.silan.service.LessionService;

@Service
public class LessionServiceImpl implements LessionService {

	@Autowired
	private LessionRepository repo;
	
	@Override
	public Lession saveUser(Lession lession) throws Exception {
		lession.setLessionId(LoginIdGenerator.loginID());
		return repo.save(lession);
	}

	@Override
	public List<Lession> listLession() throws Exception {
		
		return repo.findAll();
	}

	@Override
	public String deleteLession(String lessionId) throws Exception {
		if(repo.existsById(lessionId)) {
			repo.deleteById(lessionId);
			return "success";
		}
		return "fail";
	}

	@Override
	public Lession getLessionByLessionId(String lessionId) throws Exception {
	
		return repo.findById(lessionId).get();
	}

	@Override
	public Lession updateLession(Lession lession) throws Exception {
		
		return repo.save(lession);
	}

	@Override
	public List<String> getAllLesson() {
		return repo.getAllChapter();
	}

}
