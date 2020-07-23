package com.silan.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.silan.config.FileStorageProperties;
import com.silan.exception.FileStorageException;
import com.silan.exception.MyFileNotFoundException;
import com.silan.service.FileStorageService;
import com.silan.util.AppConstants;


@Service
public class FileStorageServiceImpl implements FileStorageService {

	private final Path fileStorageLocation;
	public static String uploadDirectory=System.getProperty("user.dir")+"/uploads";
	@Autowired
	public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException(AppConstants.FILE_STORAGE_EXCEPTION_PATH_NOT_FOUND, ex);
		}
	}

	@Override
	public String storeFile(MultipartFile file) throws IOException {
		
		if (!(file.getOriginalFilename().endsWith(AppConstants.PNG_FILE_FORMAT)||file.getOriginalFilename().endsWith(AppConstants.MP4_FILE_FORMAT) || file.getOriginalFilename().endsWith(AppConstants.JPEG_FILE_FORMAT) || file.getOriginalFilename().endsWith(AppConstants.JPG_FILE_FORMAT)))
			throw new FileStorageException(AppConstants.INVALID_FILE_FORMAT);
		
		try {
			File fds=new File(AppConstants.TEMP_DIR);
			//check Temp File available or not
			if(! fds.isDirectory()) {
				Path tempPath=Paths.get(AppConstants.TEMP_DIR);
				Files.createDirectories(tempPath);
			}
			
		}
		catch(Exception ex) {
			throw new FileStorageException(AppConstants.FILE_STORAGE_EXCEPTION_PATH_NOT_FOUND, ex);

		}
		File f = new File(AppConstants.TEMP_DIR+file.getOriginalFilename());
		
		 f.createNewFile();
			FileOutputStream fout = new FileOutputStream(f);
			fout.write(file.getBytes());
			fout.close();
			/*
			 * BufferedImage image = ImageIO.read(f); int height = image.getHeight(); int
			 * width = image.getWidth(); if(width>300 || height>300) { if(f.exists())
			 * f.delete(); throw new
			 * FileStorageException(AppConstants.INVALID_FILE_DIMENSIONS); }
			 * 
			 * if(f.exists()) f.delete();
			 */
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains(AppConstants.INVALID_FILE_DELIMITER)) {
				throw new FileStorageException(AppConstants.INVALID_FILE_PATH_NAME + fileName);
			}
			String newFileName = System.currentTimeMillis() + AppConstants.FILE_SEPERATOR + fileName;
			Path targetLocation = this.fileStorageLocation.resolve(newFileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return newFileName;
		} catch (IOException ex) {
			throw new FileStorageException(String.format(AppConstants.FILE_STORAGE_EXCEPTION, fileName), ex);
		}

	}
	
	@Override
	public Resource loadFileAsResource(String fileName) {
		Resource resource = null;
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			resource=	new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException(AppConstants.FILE_NOT_FOUND + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException(AppConstants.FILE_NOT_FOUND + fileName, ex);
		}
		
	}
}