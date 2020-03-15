
package com.schoolmonitor.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmonitor.repositories.schoolmonitor.CredentialsRepository;
import com.schoolmonitor.repositories.schools.SchoolRepository;
import com.schoolmonitor.repositories.schools.SubscriptionRepository;

/**
 * @author PrabhjeetS
 * @version 1.0 Dec 28, 2019
 */
@Service
public class StudentDataUploadServiceImpl implements StudentDataUploadService {
	@Autowired
	SchoolRepository schoolRepository;
	@Autowired
	SubscriptionRepository subscriptionRepository;
	@Autowired
	CredentialsRepository credentialsRepository;
	
	@Override
	public Void studentDataUpload(MultipartFile studentDataFile) throws IOException {
		
		InputStream inputStream=studentDataFile.getInputStream();
		File currentDirectory=new File(".");
		String currentDirectoryAbsolutePath=currentDirectory.getAbsolutePath();
		String fileLocation = currentDirectoryAbsolutePath.substring(0, currentDirectoryAbsolutePath.length() - 1) + studentDataFile.getOriginalFilename();
		FileOutputStream f = new FileOutputStream(fileLocation);
	    int ch = 0;
	    while ((ch = inputStream.read()) != -1) {
	        f.write(ch);
	    }
	    f.flush();
	    f.close();
		return null;

}}
