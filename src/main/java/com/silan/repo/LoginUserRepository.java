package com.silan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.silan.model.LoginUser;

public interface LoginUserRepository extends JpaRepository<LoginUser, String> {

	@Query("Select count(*) from LoginUser u where u.userName=:userName and u.role=:role")
	Integer checkLoginUser(String userName,String role);

	@Query("Select count(*) from LoginUser u where u.userName=:userName and u.role=:role and u.password=:password")
	Integer checkLoginAuthentication(String userName,String role,String password);
	
	
	  @Transactional
	  @Modifying
	  @Query("delete from LoginUser l where l.userName=:userName and l.role=:role ")
	  Integer deleteLoginUser(String userName,String role);
	 
	@Query(value="Select u.password from login_user u where u.user_name=:userName and u.role=:role",nativeQuery = true)
	String getLoginUser(String userName,String role);
}
