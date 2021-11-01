package com.schoolmonitor.model;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CredentialDTO  implements UserDetails{
	private static final Logger logger = LoggerFactory.getLogger(CredentialDTO.class);
	private static final long serialVersionUID = 8048896219224374881L;
	private String domain;
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}
	private String emailId;
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	private byte isAdmin;
	private String password;
	private int userId;
	private String userName;
	private Date accountCreationDate;
	private Date passwordLastChangedDate;
	private Integer numberOfRetry;
    private byte isActive;
    private boolean isStudent;
    private Collection<? extends GrantedAuthority>authorities;
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public boolean isStudent() {
		return isStudent;
	}
	public void setIsStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	@Override
	public String getPassword() {
		return password;
	}
	public Date getAccountCreationDate() {
		return accountCreationDate;
	}
	public void setAccountCreationDate(Date accountCreationDate) {
		this.accountCreationDate = accountCreationDate;
	}
	@Override
	public String getUsername() {
		return userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		//TODO
		return false;
	}
	public Date getPasswordLastChangedDate() {
		return passwordLastChangedDate;
	}
	public void setPasswordLastChangedDate(Date passwordLastChangedDate) {
		this.passwordLastChangedDate = passwordLastChangedDate;
	}
	public Integer getNumberOfRetry() {
		return numberOfRetry;
	}
	public void setNumberOfRetry(Integer numberOfRetry) {
		this.numberOfRetry = numberOfRetry;
	}
	public byte getIsActive() {
		return isActive;
	}
	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}
	public String getUserName() {
		return userName;
	}
	@Override
	public boolean isAccountNonLocked() {
		return this.numberOfRetry<3?true:false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		long passwordAge;
		if(null!=passwordLastChangedDate)
        passwordAge=ChronoUnit.DAYS.between(this.passwordLastChangedDate.toInstant(), new Date().toInstant());
		else passwordAge=ChronoUnit.DAYS.between(this.accountCreationDate.toInstant(), new Date().toInstant());
		return passwordAge<45?true:false;
	}
	@Override
	public boolean isEnabled() {
		return this.isActive==1?true:false;
	}
	public byte getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(byte isAdmin) {
		this.isAdmin = isAdmin;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
