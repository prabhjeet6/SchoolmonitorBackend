package com.schoolmonitor.controller;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmonitor.service.SchoolsService;

/**
 * @author PrabhjeetS
 * @version 1.0
   Oct 26, 2020
 */

@RestController
@RequestMapping("/schoolmonitor")
public class OnlineCourseworkController {
	

	@GetMapping(value="/findCourses")
	public ResponseEntity<?> searchForCourses() {
		return null;

	}

}
