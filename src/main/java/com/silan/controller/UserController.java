package com.silan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.silan.model.User;
import com.silan.service.IUserService;

@Controller
public class UserController {
	
	@Autowired
	private IUserService serviec;//HAS-A Releationship
	//1. UserPage
	@GetMapping({ "/userpage"})
	public String Userpage(Model model) {
		model.addAttribute("user", new User());
		return "UserRegistration";
	}
	
		
		
			
	
	//2. Save User
    @PostMapping("/userSave")
	public String saveUser(@ModelAttribute User user,Model model) {
		
		try {
			user =serviec.saveUser(user);
			if(user==null) {
				model.addAttribute("usersave", "Some Issue try again !!!!");
				model.addAttribute("user", new User());
			}
			else {
				model.addAttribute("usersave", "user save successful");
				model.addAttribute("user", new User());
			}
		}catch(Exception ex) {
			model.addAttribute("usersave", "Some Issue try again !!!!");
			model.addAttribute("user", new User());
		}
		
		return "redirect:AllUser";
	}
	
	//3. All User List
	@GetMapping("AllUser")
	public String allUser(Model model,@PageableDefault(page = 0, size = 10) Pageable p) {
		try {
	
			
				model.addAttribute("userlist", serviec.getPageUser(p));
			
			
		}
		catch(Exception e) {
			
		}
		return "UserView";
	}
	
	//4. check userName 
	@RequestMapping("/checUserName")
	public @ResponseBody String validateName(
		@RequestParam String userName
		) 
	{
	  String msg="";
	  try {
		  
		  Integer count= serviec.checkUserName(userName);
		  if(count>0) {
			msg=userName + " Already Exist";
		  }
	  }
	  catch(Exception e) {
		  
	  }
	
	  return msg;
	}
	
	// 5. show Edit Page with data
		@GetMapping("/userEdit")
		public String showEdit(@RequestParam String userName,  Model model) {
			try {
			model.addAttribute("user", serviec.getUserByserName(userName));
			return "UserEdit";
			}
			catch(Exception e) {
				e.printStackTrace();
				return "redirect:AllUser";
			}
			
		}
		
		// 6. On click update button
		@PostMapping("/userupdate")
		public String update(@ModelAttribute User user, Model model) {
			try {
			serviec.updateUser(user);
			}
			catch(Exception e) {
				
			}
			return "redirect:AllUser";
		}
		
		
		// 5. show Edit Page with data
				@GetMapping("/userremove")
				public String delete(@RequestParam String userName,  Model model) {
					try {
					model.addAttribute("user", serviec.delete(userName));
					
					}
					catch(Exception e) {
						e.printStackTrace();
						
					}
					return "redirect:AllUser";
				}
	

}
