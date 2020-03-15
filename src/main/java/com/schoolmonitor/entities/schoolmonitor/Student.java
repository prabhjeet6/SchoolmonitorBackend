package com.schoolmonitor.entities.schoolmonitor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the student database table.
 * 
 */
//TODO: AddressId and SchoolSpecificsId Columns are added additionally.
@Entity
@Table(schema="schoolmonitor",name = "student")
@NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 4)
	private String bloodGroup;

	private int contactNumber;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dateOfBirth;

	@Column(nullable = false, length = 255)
	private String fatherName;

	@Column(nullable = false, length = 255)
	private String firstName;

	@Column(nullable = false, length = 255)
	private String lastName;

	@Column(nullable = false, length = 255)
	private String motherName;

	@Column(length = 255)
	private String stream;

	@Column(length = 255)
	private String studentEmailId;
	@Id
	@Column(nullable = false, length = 255)
	private int studentId;

	

	// bi-directional one-to-one association to Credential
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studentId", referencedColumnName = "linkedStudentId", nullable = false)
	private Credential credential;

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

	// bi-directional many-to-one association to Schoolspecific
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolSpecificsId", referencedColumnName = "schoolSpecificsId", nullable = false)
	private Schoolspecific schoolspecific;
	@Column(nullable = false,insertable=false,updatable=false)
	private int schoolSpecificsId;
	@Column(unique = true, nullable = false)
	private int linkedAddressId;
	// bi-directional one-to-one association to Address
	@OneToOne(mappedBy = "student", fetch = FetchType.LAZY)
	@JoinColumn(name = "linkedAddressId", referencedColumnName = "addressId")
	private Address address;

	@Column
	private int schoolId;
//'/' Separated  values
	@Column(unique=true)
	private String classRollnumberSectionInformation;
	
	public String getClassRolenumberSectionInformation() {
		return classRollnumberSectionInformation;
	}

	public void setClassRolenumberSectionInformation(String classRollnumberSectionInformation) {
		this.classRollnumberSectionInformation = classRollnumberSectionInformation;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public Student() {
	}

	public String getBloodGroup() {
		return this.bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public int getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(int contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
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

	public String getMotherName() {
		return this.motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getStream() {
		return this.stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getStudentEmailId() {
		return this.studentEmailId;
	}

	public void setStudentEmailId(String studentEmailId) {
		this.studentEmailId = studentEmailId;
	}

	public int getStudentId() {
		return this.studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
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