package com.silan.service;

import com.silan.model.LoginUser;
import com.silan.model.Student;
import com.silan.model.User;

public interface ILoginService {

	public User userAuthentication(LoginUser loginUser)throws Exception;
	public Student studentAuthentication(LoginUser loginUser)throws Exception;
}
