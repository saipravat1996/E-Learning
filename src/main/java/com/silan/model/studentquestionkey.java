package com.silan.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class studentquestionkey implements Serializable {

private static final long serialVersionUID = 1L;
	
	private String userName;
	private String questionId;
}
