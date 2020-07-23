package com.silan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.silan.model.LoginUser;
import com.silan.model.Student;
import com.silan.model.User;
import com.silan.service.ILoginService;
import com.silan.util.AppConstants;

@Controller
public class LoginController {

	@Autowired
	private ILoginService service;
	@GetMapping({ "/home", "/" })
	public String Userpage(Model model) {
		model.addAttribute("users", new LoginUser());
		return "homeNav";
	}
	@GetMapping({ "/adminlogin"})
	public String adminlogin(Model model) {
		//model.addAttribute("loginUser", new LoginUser());
		return "AdminLogin";
	}
	@GetMapping({ "/studentlogin"})
	public String studentLogin(Model model) {
	//	model.addAttribute("loginUser", new LoginUser());
		return "StudentLogin";
	}
	@PostMapping("/login")
	public String loginAuthentication(LoginUser loginUser,Model model,HttpServletRequest request) {
		String responce=null;
		HttpSession session=null;
		try {
			if(loginUser.getRole().equals("admin")) {
				User user=service.userAuthentication(loginUser);
				if(user==null) {
					responce="adminlogin";
				}
				else {
					responce="admindashboard";
					session=request.getSession();
					session.setAttribute("userName", loginUser.getUserName());
					session.setAttribute("role", loginUser.getRole());
					BeanUtils.copyProperties(loginUser, AppConstants.staticLoginUser);
				}
				
			}
			else if(loginUser.getRole().equals("student")) {
				Student student=service.studentAuthentication(loginUser);
				if(student==null) {
					responce="studentlogin";
				}
				else {
					responce="studentdashboard";
					session=request.getSession();
					session.setAttribute("userName", loginUser.getUserName());
					session.setAttribute("role", loginUser.getRole());
					BeanUtils.copyProperties(loginUser, AppConstants.staticLoginUser);
				}
			}
			else {
				responce="homeNav";
			}
		}
		catch(Exception e) {
			responce="homeNav";
		}
		return responce;
	}
	@GetMapping("logout")
	public String logout(Model model ,HttpServletRequest request) {
		HttpSession session =request.getSession(false);
		AppConstants.staticLoginUser=null;
		session.invalidate();
		return "homeNav";
	}
}
