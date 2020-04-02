/**
 * 
 */
package com.schoolmonitor.model;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

/**
 * @author PrabhjeetS
 * @version 1.0
   Mar 27, 2020
 */
@Component
public class AddressDTO {

	public AddressDTO() {
	}
	
	private int addressId;

	private String city;

	private String landmark;

	private String linkedStudentId;

	private String linkedTeacherId;

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getLinkedStudentId() {
		return linkedStudentId;
	}

	public void setLinkedStudentId(String linkedStudentId) {
		this.linkedStudentId = linkedStudentId;
	}

	public String getLinkedTeacherId() {
		return linkedTeacherId;
	}

	public void setLinkedTeacherId(String linkedTeacherId) {
		this.linkedTeacherId = linkedTeacherId;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	private String pincode;



}
