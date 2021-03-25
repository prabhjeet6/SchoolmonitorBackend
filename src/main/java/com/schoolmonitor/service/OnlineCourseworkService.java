package com.schoolmonitor.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

/**
 * @author PrabhjeetS
 * @version 1.0 Dec 28, 2020
 */
@Service
public interface OnlineCourseworkService {
	Object searchOnlineCoursework(String searchTerm) throws IOException;
}
