package com.silan.rest;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silan.model.AppResponse;
import com.silan.model.Lession;
import com.silan.service.FileStorageService;
import com.silan.service.LessionService;
import com.silan.util.AppConstants;


@RestController
public class EmployeeRestController {

	@Autowired
	LessionService applicationService;
	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	FileStorageService fileStorageService;

	@RequestMapping(value = AppConstants.EMPLOYEE_URI, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public AppResponse createEmployee(
			@RequestParam(value = AppConstants.EMPLOYEE_JSON_PARAM, required = true) String empJson,
			@RequestParam(required = true, value = AppConstants.EMPLOYEE_FILE_PARAM) MultipartFile file)
			throws JsonParseException, JsonMappingException, IOException {
		String fileName = fileStorageService.storeFile(file);
		System.out.println(fileName);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(fileName).toUriString();
		System.out.println(fileDownloadUri);

		String[] f=fileName.split("[.]");
		
		 Lession lesson  = objectMapper.readValue(empJson, Lession.class);
		 lesson.setFilePath(fileDownloadUri);
		 lesson.setExtension(f[1]);
		lesson.setFileName(fileName);
		try {
		applicationService.saveUser(lesson);
		}
		catch(Exception e) {}

		return new AppResponse(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MSG);
	}

	@RequestMapping(value = AppConstants.EMPLOYEE_URI, method = RequestMethod.GET)
	public List<Lession> getAllEmployees() throws Exception {
		return applicationService.listLession();
	}

	@RequestMapping(value = AppConstants.DOWNLOAD_URI, method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		Resource resource = (Resource) fileStorageService.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (contentType == null) {
			contentType = AppConstants.DEFAULT_CONTENT_TYPE;
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						String.format(AppConstants.FILE_DOWNLOAD_HTTP_HEADER, resource.getFilename()))
				.body(resource);
	}
}