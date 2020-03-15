package com.schoolmonitor.entities.schoolmonitor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the credentials database table.
 * 
 */
@Entity
@Table(schema = "schoolmonitor", name = "credentials")
@NamedQuery(name = "Credential.findAll", query = "SELECT c FROM Credential c")
public class Credential implements Serializable {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date accountCreationDate;
	@Id
	@Column(nullable = false)
	private Integer userId;
	@Column(nullable = false)
	private byte isActive;

	@Column(nullable = false)
	private byte isAdmin;

	@Column(length = 255)
	private String linkedStudentId;

	@Column(length = 255)
	private String linkedTeacherId;

	private int numberOfRetry;

	@Column(nullable = false, length = 255)
	private String password;

	@Temporal(TemporalType.DATE)
	private Date passwordLastChangedDate;

	@Column(nullable = false, length = 255)
	private String userName;

	public Credential() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getAccountCreationDate() {
		return this.accountCreationDate;
	}

	public void setAccountCreationDate(Date accountCreationDate) {
		this.accountCreationDate = accountCreationDate;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public byte getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(byte isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getLinkedStudentId() {
		return this.linkedStudentId;
	}

	public void setLinkedStudentId(String linkedStudentId) {
		this.linkedStudentId = linkedStudentId;
	}

	public String getLinkedTeacherId() {
		return this.linkedTeacherId;
	}

	public void setLinkedTeacherId(String linkedTeacherId) {
		this.linkedTeacherId = linkedTeacherId;
	}

	public int getNumberOfRetry() {
		return this.numberOfRetry;
	}

	public void setNumberOfRetry(int numberOfRetry) {
		this.numberOfRetry = numberOfRetry;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getPasswordLastChangedDate() {
		return this.passwordLastChangedDate;
	}

	public void setPasswordLastChangedDate(Date passwordLastChangedDate) {
		this.passwordLastChangedDate = passwordLastChangedDate;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}