package com.silan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.silan.model.Student;

public interface StudentRepositoty extends JpaRepository<Student, String> {

	@Query("select count(*) from Student s where s.userName=:userName")
	Integer checkUserName(String userName); 
}
