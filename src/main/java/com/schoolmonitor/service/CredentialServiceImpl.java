package com.schoolmonitor.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.schoolmonitor.entities.schoolmonitor.Credential;
import com.schoolmonitor.repositories.schoolmonitor.CredentialsRepository;
/**
 * @author PrabhjeetS
 * @version 1.0
 */
public class CredentialServiceImpl implements CredentialService {
	@Autowired
	CredentialsRepository credentialsRepository;

	@Override
	public Credential findByUserNameAndPassword(String Username, String password) {
		return credentialsRepository.findByUserNameAndPassword(Username, password);
	}

}
