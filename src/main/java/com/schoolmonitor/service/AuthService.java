package com.schoolmonitor.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.schoolmonitor.model.CredentialDTO;
import com.schoolmonitor.security.AuthenticationRequest;

@Service
public interface AuthService {
	Collection<? extends GrantedAuthority> getAuthorities(Collection<String> roles);
	List<String> getUserRoles(CredentialDTO credentialDTO);
	Object signin(AuthenticationRequest data, HttpServletRequest request);
	Integer verifyEmailAndSendOTP(String domain, String emailId) throws SendFailedException;
	Object changePassword(String emailId, String domain, String newPassword);
}
