package com.schoolmonitor.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmonitor.exception.SchoolMonitorException;
import com.schoolmonitor.model.SearchInputModel;
import com.schoolmonitor.service.OnlineCourseworkService;

/**
 * @author PrabhjeetS
 * @version 1.0 Dec 28, 2020
 */
@RestController
@RequestMapping("/schoolmonitor")
public class OnlineCourseworkController {
	
	@Autowired
	OnlineCourseworkService onlineCourseworkService;

	@PostMapping(value = "/searchOnlineCoursework", headers = { "Authorization" })

	public ResponseEntity<?> searchOnlineCoursework(@RequestBody SearchInputModel searchInputModel) throws IOException {
		try{
			return ok(onlineCourseworkService.searchOnlineCoursework(searchInputModel));
		}catch(  SchoolMonitorException  e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
