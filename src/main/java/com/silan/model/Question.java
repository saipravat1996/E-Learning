package com.silan.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Question {
	
	@Id
	private String questionId;
	private Long lessionId;
	private String chapter;
	private String question;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String answer;
	
	

}
