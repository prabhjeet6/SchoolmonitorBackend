package com.schoolmonitor.model;
/**
 * @author PrabhjeetS
 * @version 1.0
   Jun 25, 2020
 */
public class ChangePasswordDTO {
public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
String emailId;
String domain;
String newPassword;
}
