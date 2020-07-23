package com.silan.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.silan.model.User;

public interface IUserService {

	public User saveUser(User user)throws Exception;
	
	public Integer checkUserName(String userName)throws Exception;
	public Integer checkUserNameStudent(String userName)throws Exception;
	
	public List<User> listUser()throws Exception;
	
	public String delete(String userName)throws Exception;
	
	public User getUserByserName(String userName)throws Exception;
	
	public User updateUser(User user)throws Exception;
	
	public Page<User> getPageUser(Pageable pageable)throws Exception;
	
	
	
}
