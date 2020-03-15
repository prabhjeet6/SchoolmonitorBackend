package com.schoolmonitor.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
/**
 * @author PrabhjeetS
 * @version 1.0
 */
public interface CustomUserDetailsService extends UserDetailsService {


	UserDetails loadUserByDomainAndUsername(String domain,String username) throws UsernameNotFoundException;

}
