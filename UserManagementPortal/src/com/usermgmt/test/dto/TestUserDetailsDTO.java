package com.usermgmt.test.dto;

import com.google.gson.Gson;

public class TestUserDetailsDTO {

	public String fullname;
	public String emailId;
	public String password;
	public String phoneNumber;
	public String department;
	public String jobTitle;
	
	public TestUserDetailsDTO() {
	}
	
	public TestUserDetailsDTO(String fullname, String emailId, String password, String phoneNumber, String department, String jobTitle) {
		this.fullname = fullname;
		this.emailId = emailId;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.department = department;
		this.jobTitle = jobTitle;
	}
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
