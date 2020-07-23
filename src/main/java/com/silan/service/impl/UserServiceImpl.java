package com.silan.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.silan.model.LoginUser;
import com.silan.model.User;
import com.silan.repo.LoginUserRepository;
import com.silan.repo.UserRepository;
import com.silan.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
  @Autowired
  private UserRepository repo;
  @Autowired
  private LoginUserRepository loginrepo;
	
  //1. User Save
	@Override
	public User saveUser(User user) throws Exception {
		user.setRole("admin");
		if(loginrepo.checkLoginUser(user.getUserName(), user.getRole())==0) {
			LoginUser loginuser =new LoginUser(user.getUserName(),user.getRole(),user.getPassword());
			loginrepo.save(loginuser);
			//Save User
			 user=repo.save(user);
		}
		else
		user=null;
		
		return user;
	}

	//Check User Name
	@Override
	public Integer checkUserName(String userName) throws Exception {
		
		return repo.checkUserName(userName);
	}

	//3. Get All User
	@Override
	public List<User> listUser() throws Exception {
		
		return repo.findAll();
	}
	
   //4. delete User
	@Override
	public String delete(String userName) throws Exception {
		if(repo.existsById(userName)) {
			 Optional<User> opt=repo.findById(userName);
			 //delete login user
			 loginrepo.deleteLoginUser(opt.get().getUserName(), opt.get().getRole());
			repo.deleteById(userName);
			return "success";
		}
		return "fail";
		
	}

	//5. get user By UserName
	@Override
	public User getUserByserName(String userName) throws Exception {
	
	
		Optional<User> opt=repo.findById(userName);
		String role= opt.get().getRole();
	
		String loginUser=loginrepo.getLoginUser(userName, role);
		User user=opt.get();
		user.setPassword(loginUser);
		return user;
	}

	//6. update User
	@Override
	public User updateUser(User user) throws Exception {
		
		//. Set role
		user.setRole("admin");
		//check user Name available or not
		if(loginrepo.checkLoginUser(user.getUserName(), user.getRole())==1) {
			LoginUser loginuser =new LoginUser(user.getUserName(),user.getRole(),user.getPassword());
			loginrepo.save(loginuser);
			//Save User
			user=repo.save(user);
		}
		else
			user=null;
		
		return user;
	}


	@Override
	public Page<User> getPageUser(Pageable pageable) throws Exception {
		
		return repo.findAll(pageable);
	}

	@Override
	public Integer checkUserNameStudent(String userName) throws Exception {
		
		return repo.checkUserNamestudent(userName);
	}

}
