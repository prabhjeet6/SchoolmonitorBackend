package com.schoolmonitor.entities.schoolmonitor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the schoolspecifics database table.
 * 
 */
@Entity
@Table(schema="schoolmonitor",name = "schoolspecifics")
@NamedQuery(name = "Schoolspecific.findAll", query = "SELECT s FROM Schoolspecific s")
public class Schoolspecific implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length = 255)
	private String branchName;

	@Column(nullable = false, length = 255)
	private String district;

	@Column(nullable = false, length = 255)
	private String pincode;

	@Column(nullable = false, length = 255)
	private String schoolAddress;

	@Column(nullable = false)
	private int schoolContactNumber;

	@Column(length = 255)
	private String schoolEmailId;
	@Id
	@Column
	private int schoolSpecificsId;

	public int getSchoolSpecificsId() {
		return schoolSpecificsId;
	}

	// bi-directional many-to-one association to Student
	@OneToMany(mappedBy = "schoolspecific")
	private List<Student> students;

	// bi-directional many-to-one association to Teacher
	@OneToMany(mappedBy = "schoolspecific")
	private List<Teacher> teachers;

	public Schoolspecific() {
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPincode() {
		return this.pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getSchoolAddress() {
		return this.schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

	public int getSchoolContactNumber() {
		return this.schoolContactNumber;
	}

	public void setSchoolContactNumber(int schoolContactNumber) {
		this.schoolContactNumber = schoolContactNumber;
	}

	public String getSchoolEmailId() {
		return this.schoolEmailId;
	}

	public void setSchoolEmailId(String schoolEmailId) {
		this.schoolEmailId = schoolEmailId;
	}

	public List<Student> getStudents() {
		return this.students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Student addStudent(Student student) {
		getStudents().add(student);
		student.setSchoolspecific(this);

		return student;
	}

	public Student removeStudent(Student student) {
		getStudents().remove(student);
		student.setSchoolspecific(null);

		return student;
	}

	public List<Teacher> getTeachers() {
		return this.teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public Teacher addTeacher(Teacher teacher) {
		getTeachers().add(teacher);
		teacher.setSchoolspecific(this);

		return teacher;
	}

	public Teacher removeTeacher(Teacher teacher) {
		getTeachers().remove(teacher);
		teacher.setSchoolspecific(null);

		return teacher;
	}

	public void setSchoolSpecificsId(int schoolSpecificsId) {
		this.schoolSpecificsId = schoolSpecificsId;
	}

}