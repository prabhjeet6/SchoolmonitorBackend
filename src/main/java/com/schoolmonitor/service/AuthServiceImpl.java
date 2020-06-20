package com.schoolmonitor.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.schoolmonitor.model.CredentialDTO;
import com.schoolmonitor.model.TenantContext;
import com.schoolmonitor.repositories.schoolmonitor.CredentialsRepository;
import com.schoolmonitor.repositories.schoolmonitor.StudentRepository;
import com.schoolmonitor.repositories.schoolmonitor.TeachersRepository;
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
	StudentRepository studentRepository;
	@Autowired
	TeachersRepository teachersRepository;

	@Autowired
	public JavaMailSender emailSender;

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

	@Override
	public Integer verifyEmailAndSendOTP(String domain,String emailId) throws SendFailedException {
		
		TenantContext.setCurrentTenant(domain);
		Integer oneTimePassword = null;
		if (null != studentRepository.findByStudentEmailId(emailId)
				|| null != teachersRepository.findByTeacherEmailId(emailId)) {
			oneTimePassword = (int) (Math.random() * 10000);
			
			
			this.sendMessage(emailId, "OneTimePassword","Hi,\n\n"+"Your verification Code for Password Recovery is "+addPaddingToOTP(oneTimePassword)+"\n\nRegards,\nSchoolmonitor Support");
		}
		return oneTimePassword;
	}
	static String addPaddingToOTP(Integer oneTimePassword){
		int num=oneTimePassword,count=0;
		for(; num != 0; num/=10, ++count);
		if(count==3)			
		return "0"+oneTimePassword;
		else if(count==2)
			return "00"+oneTimePassword;
		else if(count==1)
			return "000"+oneTimePassword;
		else return oneTimePassword.toString();
		
	}
	public void sendMessage(String to, String subject, String text) throws SendFailedException {
SimpleMailMessage message = new SimpleMailMessage();
message.setTo(to);
message.setSubject(subject);
message.setText(text);
emailSender.send(message);
	}
}
