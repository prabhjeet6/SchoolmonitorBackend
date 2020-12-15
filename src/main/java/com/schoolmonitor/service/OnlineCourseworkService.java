package com.schoolmonitor.service;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author PrabhjeetS
 * @version 1.0 Oct 26, 2020
 */
@Service
public interface OnlineCourseworkService {
    List<Object> searchForCourses();
}
