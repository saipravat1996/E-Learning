package com.silan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LoginCompositekey.class)
@Table(name="Login_user")
public class LoginUser {


	@Id
	@Column(name="user_name")
	private String userName;
	@Id
	@Column(name="role")
	private String role;
	@NotNull
	@Column(name="password")
	private String password;

}
