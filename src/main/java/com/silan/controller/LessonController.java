package com.silan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.silan.model.Lession;
import com.silan.service.FileStorageService;
import com.silan.service.LessionService;
import com.silan.util.AppConstants;


@Controller
public class LessonController {

	@Autowired
	private LessionService service;
	@Autowired
	FileStorageService fileStorageService;
	@GetMapping({ "/lessionRegistration"})
	public String lessionReg(Model model) {
		model.addAttribute("lesson", new Lession());
		return "LessonRegistration";
	}
	@RequestMapping(value = "FileDownLoad", method = RequestMethod.GET)
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
	
	//1. UserPage
		@GetMapping({ "/LessonView"})
		public String lession(Model model) {
			try {
			model.addAttribute("listLesson", service.listLession());
			}
			catch(Exception ex) {
				model.addAttribute("listLesson", null);

			}
			return "LessonView";
		}
		
   //3. get lesson by lessonId
		@GetMapping("getLessonById")
		public String getLessonId(Model model ,@RequestAttribute String lessonId) {
			
			try {
				model.addAttribute("lesson", service.getLessionByLessionId(lessonId));
			}
			catch(Exception e) {
				model.addAttribute("Error", "Not value");
				e.printStackTrace();
			}
			return "EditLesson";
		}
		@PostMapping("lessonSave")
		public String lessonSave(Model model,Lession lesson) {
			try {
				MultipartFile file=lesson.getFile();
				String fileName = fileStorageService.storeFile(file);
				System.out.println(fileName);
				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
						.path(fileName).toUriString();
				System.out.println(fileDownloadUri);
				lesson.setFileName(fileName);
				lesson.setFilePath(fileDownloadUri);
				service.saveUser(lesson);
			   model.addAttribute("lessonUpdate", "Lesson Upated Successfully");	
			    }
			catch(Exception e) {
				   					model.addAttribute("lessonUpdate", "Lesson Upated unsSuccessfully");	

			                     }
			    return "redirect:LessonView";
		}
	//4. Update Lesson 
		@PostMapping("lessonUpdate")
		public String lessonUpdate(Model model,Lession lesson) {
			try {
				service .updateLession(lesson);
			   model.addAttribute("lessonUpdate", "Lesson Upated Successfully");	
			    }
			catch(Exception e) {
				   					model.addAttribute("lessonUpdate", "Lesson Upated unsSuccessfully");	

			                     }
			    return "redirect:LessonView";
		}

	//5. Lesson Delete By ID
		public String lessonDelete(Model model,@RequestAttribute String lessonId) {
			try {
			service.deleteLession(lessonId);
			model.addAttribute("LessonRemove", "Lesson remove successfully");
			}
			catch(Exception e) {
				model.addAttribute("LessonRemove", "Lesson remove unsuccessfully");
			}
			return "redirect:LessonView";
		}
		
		//5. Lesson Delete By ID
		        @ResponseBody
		        @GetMapping("allLession")
				public List<String> getAllChapter(Model model) {
		        	List<String > list=null;
					try {
				 list=service.getAllLesson();
					//model.addAttribute("allChapter",list);
					}
					catch(Exception e) {
						
					}
					return list;
				}
}
    