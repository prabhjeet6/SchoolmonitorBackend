/**
 * 
 */
package com.schoolmonitor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmonitor.repositories.schools.SchoolRepository;

/**
 * @author PrabhjeetS
 * @version 1.0
   Jun 12, 2020
 */
@Service
public class SchoolsServiceImpl implements SchoolsService{

	@Autowired
	SchoolRepository schoolRepository;
	@Override
	public List<String> getSchoolDomains() {
		return schoolRepository.findDomainForLogin();
	}

	
	

}
