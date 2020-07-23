package com.silan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	@Id
	@Column(name="user_name")
	private String userName;
	@Transient
	private String password;
	private String fName;
	private String lName;
	private String address;
	private String contact;
	private String role;

	
}
