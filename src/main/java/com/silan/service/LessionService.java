package com.silan.service;

import java.util.List;

import com.silan.model.Lession;

public interface LessionService {

	
public Lession saveUser(Lession lession)throws Exception;
	
	
	public List<Lession> listLession()throws Exception;
	
	public String deleteLession(String lessionId)throws Exception;
	
	public Lession getLessionByLessionId(String lessionId)throws Exception;
	
	public Lession updateLession(Lession lession)throws Exception;
	
	public List<String> getAllLesson();
}
