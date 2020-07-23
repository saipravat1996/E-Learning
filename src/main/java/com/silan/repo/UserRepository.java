package com.silan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.silan.model.User;

public interface UserRepository extends JpaRepository<User, String> {

	@Query("select count(*) from User u where u.userName=:userName")
	Integer checkUserName(String userName); 
	
	@Query("select count(*) from Student u where u.userName=:userName")
	Integer checkUserNamestudent(String userName);
}
