
package com.schoolmonitor.service;

import java.io.IOException;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author PrabhjeetS
 * @version 1.0
   Dec 28, 2019
 */
@Service
public interface StudentDataUploadService {
 
	Void studentDataUpload(MultipartFile studentDataFile,HttpServletRequest request) throws IOException, InvalidFormatException;
	
}
