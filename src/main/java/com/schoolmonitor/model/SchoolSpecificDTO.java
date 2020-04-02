/**
 * 
 */
package com.schoolmonitor.model;

import org.springframework.stereotype.Component;

/**
 * @author PrabhjeetS
 * @version 1.0
   Mar 17, 2020
 */
@Component
public class SchoolSpecificDTO {

	public SchoolSpecificDTO() {
	}
	private int schoolSpecificsId;
	public int getSchoolSpecificsId() {
		return schoolSpecificsId;
	}
	public void setSchoolSpecificsId(int schoolSpecificsId) {
		this.schoolSpecificsId = schoolSpecificsId;
	}
	private String branchName;
	private String district;
	private String pincode;
	private String schoolAddress;
	private int schoolContactNumber;
	private String schoolEmailId;
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getSchoolAddress() {
		return schoolAddress;
	}
	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}
	public int getSchoolContactNumber() {
		return schoolContactNumber;
	}
	public void setSchoolContactNumber(int schoolContactNumber) {
		this.schoolContactNumber = schoolContactNumber;
	}
	public String getSchoolEmailId() {
		return schoolEmailId;
	}
	public void setSchoolEmailId(String schoolEmailId) {
		this.schoolEmailId = schoolEmailId;
	}
	

}
