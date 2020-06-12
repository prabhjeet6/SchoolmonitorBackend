/**
 * 
 */
package com.schoolmonitor.service;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author PrabhjeetS
 * @version 1.0 Jun 12, 2020
 */
@Service
public interface SchoolsService {
	List<String> getSchoolDomains();
}
