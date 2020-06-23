package com.schoolmonitor.entities.schoolmonitor;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the teacher database table.
 * 
 */
@Entity
@Table(schema="schoolmonitor",name = "teacher")
@NamedQuery(name = "Teacher.findAll", query = "SELECT t FROM Teacher t")
public class Teacher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 255)
	private String bloodGroup;

	@Column(length = 45)
	private String department;

	@Column(length = 45)
	private String designation;

	@Column(nullable = false, length = 255)
	private String firstName;

	@Column(nullable = false, length = 255)
	private String lastName;
    
	@Column(nullable = false, length = 1)
	private String gender;
	

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	// bi-directional one-to-one association to Credential
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId", referencedColumnName = "linkedTeacherId", nullable = false)
	private Credential credential;

	// bi-directional many-to-one association to Schoolspecific
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolSpecificsId", referencedColumnName = "schoolSpecificsId", nullable = false)
	private Schoolspecific schoolspecific;
	@Column(nullable = false,updatable=false,insertable=false)
	private int schoolSpecificsId;
	// bi-directional one-to-one association to Address
	@OneToOne(mappedBy = "teacher", fetch = FetchType.LAZY)
	@JoinColumn(name = "linkedAddressId", referencedColumnName = "addressId", nullable = false)
	private Address address;
	@Column(unique = true)
	private int linkedAddressId;

	public int getSchoolSpecificsId() {
		return schoolSpecificsId;
	}
	

	public void setSchoolSpecificsId(int schoolSpecificsId) {
		this.schoolSpecificsId = schoolSpecificsId;
	}

	public int getLinkedAddressId() {
		return linkedAddressId;
	}

	public void setLinkedAddressId(int linkedAddressId) {
		this.linkedAddressId = linkedAddressId;
	}

	@Id
	@Column
	private int teacherId;

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public Teacher() {
	}

	public String getBloodGroup() {
		return this.bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	

	public Credential getCredential() {
		return this.credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	public Schoolspecific getSchoolspecific() {
		return this.schoolspecific;
	}

	public void setSchoolspecific(Schoolspecific schoolspecific) {
		this.schoolspecific = schoolspecific;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}