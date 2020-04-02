package com.schoolmonitor.entities.schoolmonitor;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

/**
 * The persistent class for the address database table.
 * 
 */

//TODO : linkedStudentId and linkedTeacherId could be removed from table and entity

@Entity
@Table(schema="schoolmonitor",name = "address")
@NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int addressId;

	@Column(length = 255)
	private String city;

	@Column(length = 255)
	private String landmark;

	@Column(length = 45)
	private String linkedStudentId;

	@Column(length = 45)
	private String linkedTeacherId;

	@Column(length = 255)
	private String pincode;

	// bi-directional one-to-one association to Student
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "addressId", referencedColumnName = "linkedAddressId", nullable = false)
	private Student student;

	// bi-directional one-to-one association to Teacher
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "addressId", referencedColumnName = "linkedAddressId", nullable = false)
	private Teacher teacher;

	public Address() {
	}

	public int getAddressId() {
		return this.addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLandmark() {
		return this.landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
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

	public String getPincode() {
		return this.pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}