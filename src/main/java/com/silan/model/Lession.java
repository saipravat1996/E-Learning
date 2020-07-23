package com.silan.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lession {

	@Id
	private String lessionId;
	private String chapter;
	private String titel;
	private String fileType;;
	private String filePath;
	private String fileName;
	private String extension;
	@Transient
	private MultipartFile file;
	
	
}
