package com.silan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.silan.model.StudentResult;

public interface StudentResultRepository extends JpaRepository<StudentResult, String> {

	@Query(value="select * from student_result where user_name=:userName",nativeQuery = true)
	List<StudentResult> getStudentResult(String userName);
}
