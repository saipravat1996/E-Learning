package com.silan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silan.model.LoginUser;
import com.silan.model.Student;
import com.silan.model.User;
import com.silan.repo.LoginUserRepository;
import com.silan.repo.StudentRepositoty;
import com.silan.repo.UserRepository;
import com.silan.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
private LoginUserRepository repo;
	@Autowired
	private UserRepository userrepo;
	@Autowired
	private StudentRepositoty studentrepo;

	@Override
	public User userAuthentication(LoginUser loginUser) throws Exception {
		User user=null;
		if(repo.checkLoginAuthentication(loginUser.getUserName(), loginUser.getRole(),loginUser.getPassword())==1) {
			user=userrepo.findById(loginUser.getUserName()).get();
		}
		return user;
	}

	@Override
	public Student studentAuthentication(LoginUser loginUser) throws Exception {
		Student student=null;
		if(repo.checkLoginAuthentication(loginUser.getUserName(), loginUser.getRole(),loginUser.getPassword())==1) {
			student=studentrepo.findById(loginUser.getUserName()).get();
		}
		return student;
	}

}
