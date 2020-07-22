
package com.schoolmonitor.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmonitor.exception.SchoolMonitorException;
import com.schoolmonitor.service.StudentDataUploadService;

/**
 * @author PrabhjeetS
 * @version 1.0 Dec 28, 2019
 */
@RestController
@RequestMapping("/schoolmonitor")
public class StudentDataUploadController {

	@Autowired
	public StudentDataUploadController(StudentDataUploadService studentDataUploadService) {
		this.studentDataUploadService = studentDataUploadService;
	}

	private final StudentDataUploadService studentDataUploadService;
    
	@PostMapping(value = "/studentDataUpload", headers= {"Authorization"},consumes = { "multipart/form-data","application/octet-stream" })

	public ResponseEntity<?> studentDataUpload(@RequestParam("studentDataFile") MultipartFile   studentDataFile) {
		try {
			return ok(studentDataUploadService.studentDataUpload(studentDataFile));
		} catch (IOException | InvalidFormatException e) {
			//throw new SchoolMonitorException(e);
			System.err.println(e);
			return null;
		}

	}

}
