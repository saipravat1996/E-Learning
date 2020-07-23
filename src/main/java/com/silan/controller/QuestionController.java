package com.silan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.silan.model.Question;
import com.silan.model.User;
import com.silan.service.QuestionService;

@Controller
public class QuestionController {

	@Autowired
	private QuestionService service;
	//1. UserPage
	@GetMapping({ "/questionview"})
	public String questionview(Model model) {
		try {
		model.addAttribute("listQuestion", service.listQuestion());
		}catch(Exception e) {
			model.addAttribute("listQuestion", null);

		}
		return "QuestionView";
	}
	
	
	//1. UserPage
		@GetMapping({ "/studentquestionview"})
		public String studentquestionview(Model model) {
			try {
			model.addAttribute("listQuestion", service.listQuestion());
			}catch(Exception e) {
				model.addAttribute("listQuestion", null);

			}
			return "StudentQuestionView";
		}
	//1. UserPage
	@GetMapping({ "/questionregistration"})
	public String question(Model model) {
		model.addAttribute("user", new User());
		return "QuestionRegistration";
	}
	
	
	@GetMapping("/questionById")
	public String questionById(String questionID,Model model) {
		try {
			model.addAttribute("question", service.getLessionByQuestionId(questionID));
		}
		catch(Exception e) {
			
		}
		return "QuestionEdit";
	}
	
	@GetMapping("/questionAttend")
	public String questionattendByStudent(String questionID,Model model) {
		try {
			model.addAttribute("question", service.getLessionByQuestionId(questionID));
		}
		catch(Exception e) {
			
		}
		return "StudentQuestionAttend";
	}
	
	@PostMapping("/SaveQuestion")
	public String questionSave(Model model,Question question) {
		try {
			service.saveQuestion(question);
			model.addAttribute("Questionadd", "Question Save Successfully ");
		}
		catch(Exception e) {
			model.addAttribute("Questionadd", "Question Save unsuccessfully ");

		}
		return "redirect:questionview";
	}
	
	@PostMapping("/UpdateQuestion")
	public String questionUpdate(Model model,Question question) {
		try {
			service.updateQuestion(question);
			model.addAttribute("Questionadd", "Question Update Successfully ");
		}
		catch(Exception e) {
			model.addAttribute("Questionadd", "Question Update unsuccessfully ");

		}
		return "redirect:questionview";
	}
	
	@GetMapping("/questionRemoveById")
	public String questionRemoveById( String questionID,Model model) {
		try {
			service.deleteQuestion(questionID);
			model.addAttribute("Questionremove", "Question remove successfully ");
		}
		catch(Exception e) {
			model.addAttribute("Questionremove", "Question remove unsuccessfully ");

		}
		return "redirect:questionview";
	}
}
