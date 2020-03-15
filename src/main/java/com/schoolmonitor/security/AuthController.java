package com.schoolmonitor.security;

import static org.springframework.http.ResponseEntity.ok;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmonitor.service.AuthService;

/**
 * @author PrabhjeetS
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody AuthenticationRequest data, HttpServletRequest request) {

		try {
			return ok(authService.signin(data, request));
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}
}
