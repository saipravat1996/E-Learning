package com.silan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.silan.model.LoginUser;
import com.silan.model.Student;
import com.silan.model.StudentResult;
import com.silan.service.IUserService;
import com.silan.service.StudentService;
import com.silan.util.AppConstants;

@Controller
public class StudentController {

	@Autowired
	private StudentService service;
	@Autowired
	private IUserService serviec;//HAS-A Releationship
	//1. UserPage
	@GetMapping({ "/studentview"})
	public String studentList(Model model) {
		try {
		model.addAttribute("studentresult", service.listStudent());
		}
		catch(Exception e) {
			
		}
		return "StudentView";
	}
	@GetMapping({ "/studentViewlesson"})
	public String studentViewLesson(Model model) {
		model.addAttribute("student", new Student());
		return "StudentViewLesson";
	}
	
	@GetMapping({ "/studentRegistation"})
	public String studentreg(Model model) {
		model.addAttribute("student", new Student());
		return "StudentRegistration";
	}
	@PostMapping({ "/studentSaveResult"})
	public String saveStudentResult(Model model,StudentResult studentResult) {
		String responce=null;
		try {
		if(AppConstants.staticLoginUser !=null) {
			studentResult.setUserName(AppConstants.staticLoginUser.getUserName());
			service.saveStudentResult(studentResult);
			responce="StudentDashboard";
		}
		else {
			responce="homeNav";
		}
		}
		catch(Exception e) {
			responce="StudentDashboard";
		}
		return responce;
	}
	@GetMapping({ "/studentViewResult"})
	public String getStudentResult(Model model) {
		String responce=null;
		try {
		if(AppConstants.staticLoginUser.getUserName() !=null) {
			
			List<StudentResult> list=service.getStudentresult(AppConstants.staticLoginUser.getUserName());
			model.addAttribute("studentresult", list);
			responce="StudentResultView";
		}
		else {
			responce="homeNav";
		}
		}
		catch(Exception e) {
			responce="StudentDashboard";
		}
		return responce;
	}
	
	@GetMapping({ "/AdminViewResult"})
	public String getStudentResult(Model model,String userName) {
		String responce=null;
		try {
	
			List<StudentResult> list=service.getStudentresult(userName);
			model.addAttribute("studentresult", list);
			responce="AdminViewStudentResult";
		
		
		}
		catch(Exception e) {
			responce="Admindashboard";
		}
		return responce;
	}
	
	@GetMapping({ "/profile"})
	public String getProfile(Model model) {
		String responce=null;
		try {
		if(AppConstants.staticLoginUser.getUserName() !=null) {
			
			//String r=AppConstants.staticLoginUser.getUserName()		;	
			//List<StudentResult> list=service.getStudentresult(AppConstants.staticLoginUser.getUserName());
			Student student=service.getStudentByserName(AppConstants.staticLoginUser.getUserName());
			model.addAttribute("profile", student);
			responce="StudentProfile";
		}
		else {
			responce="homeNav";
		}
		}
		catch(Exception e) {
			responce="StudentDashboard";
		}
		return responce;
	}
	
	//4. check userName 
		@RequestMapping("/checkStudentusername")
		public @ResponseBody String validateName(
			@RequestParam String userName
			) 
		{
		  String msg="";
		  try {
			  
			  Integer count= serviec.checkUserNameStudent(userName);
			  if(count>0) {
				msg=userName + " Already Exist";
			  }
		  }
		  catch(Exception e) {
			  
		  }
		
		  return msg;
		}
	@GetMapping("studentById")
	public String StudentById(Model model,@RequestAttribute String userName) {
		try {
			service.getStudentByserName(userName);
			model.addAttribute("student", service.getStudentByserName(userName));
		}
		catch(Exception e) {
			 e.printStackTrace();
			 model.addAttribute("student", "No student");
		}
		return "studentEdit";
	}
	@PostMapping("studentSave")
	public String studentSave(Model model,Student student) {
		try {
			service.saveStudent(student);
			model.addAttribute("student registerd", "Student registrd sucessfully." );
			model.addAttribute("loginuser", new LoginUser() );
		}
		catch(Exception e) {
			model.addAttribute("student registerd", "Student registrd unsucessfully." );
			model.addAttribute("loginuser", new LoginUser() );
		}
		return "studentlogin";
	}
}
