package com.schoolmonitor.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.schoolmonitor.entities.schoolmonitor.Credential;
import com.schoolmonitor.model.CredentialDTO;
import com.schoolmonitor.model.TenantContext;
import com.schoolmonitor.repositories.schoolmonitor.CredentialsRepository;
import com.schoolmonitor.security.AuthenticationRequest;
import com.schoolmonitor.security.JwtTokenProvider;

/**
 * @author PrabhjeetS
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
	@Autowired
	CredentialsRepository credentialsRepository;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	CredentialDTO credentialDTO;
	@Autowired
	CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

	@Autowired
	PasswordEncoder passwordEncoder;

	public Collection<? extends GrantedAuthority> getAuthorities(Collection<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	public List<String> getUserRoles(CredentialDTO credentialDTO) {

		List<String> roles = new ArrayList<String>();
		if (null != credentialDTO) {
			if (credentialDTO.isStudent())
				roles.add("Student User");
			else
				roles.add("Teacher User");
			if (credentialDTO.getIsAdmin() == 1)
				roles.add("Administrator");
		} else
			roles.add("Guest User");
		return roles;
	}

	public Object signin(@RequestBody AuthenticationRequest data, HttpServletRequest request) {

		TenantContext.setCurrentTenant(data.getDomain());
		
		credentialDTO = (CredentialDTO) customUserDetailsServiceImpl.loadUserByDomainAndUsername(data.getDomain(),
				data.getUsername());
		List<String> roles = this.getUserRoles(credentialDTO);
		UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(data.getUsername(),
				data.getPassword(), this.getAuthorities(roles));
		if (null != authtoken.getCredentials()
				&& passwordEncoder.matches(authtoken.getCredentials().toString(), credentialDTO.getPassword())) {

			authtoken.setDetails(new WebAuthenticationDetails(request));

			String token = jwtTokenProvider.createToken(data.getUsername(), this.getUserRoles(credentialDTO));
			Map<Object, Object> model = new HashMap<>();
			model.put("Username", data.getUsername());
			model.put("Token", token);

			HttpSession session = request.getSession(true);
			session.setAttribute("Username", data.getUsername());
			session.setAttribute("Token", token);
			session.setAttribute("Domain", data.getDomain());
			return model;
		} else {
			throw new BadCredentialsException("Credentials do not match with the expected User");
		}

	}

}
