package com.silan.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silan.model.LoginUser;
import com.silan.model.Student;
import com.silan.model.StudentResult;
import com.silan.repo.LoginUserRepository;
import com.silan.repo.StudentRepositoty;
import com.silan.repo.StudentResultRepository;
import com.silan.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentResultRepository resultrepo;
	@Autowired
	private StudentRepositoty repo;
	  @Autowired
	  private LoginUserRepository loginrepo;
	  
	  //1. Save Student
	@Override
	public Student saveStudent(Student student) throws Exception {
		
		//. Set role
		student.setRole("student");
		//check user Name available or not
		if(loginrepo.checkLoginUser(student.getUserName(), student.getRole())==0) {
			LoginUser loginuser =new LoginUser(student.getUserName(),student.getRole(),student.getPassword());
			loginrepo.save(loginuser);
			//Save User
			 student=repo.save(student);
		}
		else
		student=null;
		
		return student;
	}

	
	//2. Check UserNAme available or not
	@Override
	public Integer checkUserName(String userName) throws Exception {
		
		return repo.checkUserName(userName);
	}

	
	//3. Get All Student
	@Override
	public List<Student> listStudent() throws Exception {
	
		return repo.findAll();
	}

	
	//4. delete user
	@Override
	public String delete(String userName) throws Exception {
		 if(repo.existsById(userName)) {
			 Optional<Student> opt=repo.findById(userName);
			 //delete login user
			 loginrepo.deleteLoginUser(opt.get().getUserName(), opt.get().getRole());
			 //delete student
			repo.deleteById(userName);
			return "success";
			 
		 }
		return "fail";
	}

	//5. Get Student By userName
	@Override
	public Student getStudentByserName(String userName) throws Exception {
		
		Optional<Student> opt=repo.findById(userName);
		String loginUser=loginrepo.getLoginUser(opt.get().getUserName(), opt.get().getRole());
		Student student=opt.get();
		student.setPassword(loginUser);
		return student;
	}

	//6. Update Student
	@Override
	public Student updateStudent(Student student) throws Exception {
		//. Set role
				student.setRole("student");
				//check user Name available or not
				if(loginrepo.checkLoginUser(student.getUserName(), student.getRole())==1) {
					LoginUser loginuser =new LoginUser(student.getUserName(),student.getRole(),student.getPassword());
					loginrepo.save(loginuser);
					//Save User
					 student=repo.save(student);
				}
				else
				student=null;
				
				return student;
		
	}


	@Override
	public StudentResult saveStudentResult(StudentResult studentResult) throws Exception {
		
		return resultrepo.save(studentResult);
	}


	@Override
	public List<StudentResult> getStudentresult(String userName) throws Exception {
		
		return resultrepo.getStudentResult(userName);
	}

}
