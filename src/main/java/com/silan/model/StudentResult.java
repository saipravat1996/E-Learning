package com.silan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(studentquestionkey.class)
@Table(name="student_result")
public class StudentResult {

	@Id
	@Column(name="question_id")
	private String questionId;
	@Id
	@Column(name="user_name")
	private String userName;
	private String chapter;
	private String question;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String answer;
}
