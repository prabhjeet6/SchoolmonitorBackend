
package com.schoolmonitor.controller;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmonitor.service.StudentDataUploadService;

/**
 * @author PrabhjeedtS
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
    
	@PostMapping(value = "/studentDataUpload", headers= {"Authorization"},consumes = { "multipart/form-data" })

	public ResponseEntity<Boolean> studentDataUpload(@RequestParam("studentDataFile") MultipartFile   studentDataFile) {
		try {
			return new ResponseEntity<Boolean>(studentDataUploadService.studentDataUpload(studentDataFile),HttpStatus.OK);
		} catch (IOException | InvalidFormatException e) {
			//throw new SchoolMonitorException(e);
			System.err.println(e);
			return  new ResponseEntity<Boolean>(false,HttpStatus.CONFLICT);

		}

	}

}
