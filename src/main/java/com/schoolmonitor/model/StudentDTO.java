/**
 * 
 */
package com.schoolmonitor.model;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author PrabhjeetS
 * @version 1.0 Mar 27, 2020
 */
@Component
public class StudentDTO {

	public StudentDTO() {
	}

	private int studentId;
	private String firstName;
	private String lastName;
	private int schoolId;
	private String stream;
	private String fatherName;
	private String motherName;
	private String bloodGroup;
	private int contactNumber;
	private String linkedAddressId;
	private Date dateOfBirth;
	private int schoolSpecificsId;
	private String classRollnumberSectionInformation;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public int getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(int contactNumber) {
		this.contactNumber = contactNumber;
	}

	

	public String getLinkedAddressId() {
		return linkedAddressId;
	}

	public void setLinkedAddressId(String linkedAddressId) {
		this.linkedAddressId = linkedAddressId;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getSchoolSpecificsId() {
		return schoolSpecificsId;
	}

	public void setSchoolSpecificsId(int schoolSpecificsId) {
		this.schoolSpecificsId = schoolSpecificsId;
	}

	public String getClassRollnumberSectionInformation() {
		return classRollnumberSectionInformation;
	}

	public void setClassRollnumberSectionInformation(String classRollnumberSectionInformation) {
		this.classRollnumberSectionInformation = classRollnumberSectionInformation;
	}

}
