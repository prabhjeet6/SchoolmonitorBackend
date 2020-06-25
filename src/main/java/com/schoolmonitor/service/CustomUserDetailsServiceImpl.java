package com.schoolmonitor.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.schoolmonitor.entities.schoolmonitor.Credential;
import com.schoolmonitor.entities.schools.School;
import com.schoolmonitor.entities.schools.Subscription;
import com.schoolmonitor.exception.SchoolMonitorException;
import com.schoolmonitor.model.CredentialDTO;
import com.schoolmonitor.repositories.schoolmonitor.CredentialsRepository;
import com.schoolmonitor.repositories.schools.SchoolRepository;
import com.schoolmonitor.repositories.schools.SubscriptionRepository;

/**
 * @author PrabhjeetS
 * @version 1.0
 */
@Component
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	@Autowired
	private CredentialsRepository credentialsRepository;

	@Autowired
	private CredentialDTO credentialDTO;

	@Autowired
	private SchoolRepository schoolRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	

	@Autowired
	AuthService authService;

	@Override
	public UserDetails loadUserByDomainAndUsername(String domain, String username)
			throws UsernameNotFoundException, SchoolMonitorException {
		try {
			School school = schoolRepository.findByDomainForLogin(domain);
			Subscription subscription = subscriptionRepository.findById(school.getSubscriptionId()).get();
			Date currentDate = new Date();
			if (currentDate.compareTo(subscription.getSubscribedTo()) <= 0
					&& currentDate.compareTo(subscription.getSubscribedFrom()) >= 0) {
				Credential attemptedCredential=credentialsRepository.findByUserName(username);
				if (null != attemptedCredential&&attemptedCredential.getIsActive()==1) {
					credentialDTO.setDomain(domain);
					return this.loadUserByUsername(username);
				}
			}
		} catch (Throwable ex) {
			throw new SchoolMonitorException(ex);
		}

		return null;

	}

	public CustomUserDetailsServiceImpl() {
		super();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {

			Credential credential = this.credentialsRepository.findByUserName(username);
			credentialDTO.setIsStudent(null != credential.getLinkedStudentId() ? true : false);
			List<String> roles = authService.getUserRoles(credentialDTO);
			BeanUtils.copyProperties(credentialsRepository.findByUserName(username), credentialDTO);
			credentialDTO.setAuthorities(authService.getAuthorities(roles));
			return credentialDTO;
		} catch (Exception ex) {
			throw new SchoolMonitorException(ex);
		}
	}

}