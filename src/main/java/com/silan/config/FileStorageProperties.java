package com.silan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.silan.util.AppConstants;

import lombok.Data;

@ConfigurationProperties(prefix = AppConstants.FILE_PROPERTIES_PREFIX)
@Configuration
@Data
public class FileStorageProperties {
//	public static String uploadDirectory=System.getProperty("user.dir")+"/uploads";
	
	public  String uploadDir;

}