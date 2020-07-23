package com.silan.service;

import java.util.List;

import com.silan.model.Student;
import com.silan.model.StudentResult;

public interface StudentService {

	public Student saveStudent(Student student)throws Exception;
	
	public Integer checkUserName(String userName)throws Exception;
	
	public List<Student> listStudent()throws Exception;
	
	public String delete(String userName)throws Exception;
	
	public Student getStudentByserName(String userName)throws Exception;
	
	public Student updateStudent(Student student)throws Exception;
	
	public StudentResult saveStudentResult(StudentResult studentResult)throws Exception;
	public List<StudentResult> getStudentresult(String  userName)throws Exception;
}
