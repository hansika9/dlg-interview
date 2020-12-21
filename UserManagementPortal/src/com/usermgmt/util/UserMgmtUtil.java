package com.usermgmt.util;

import org.springframework.stereotype.Component;

import com.usermgmt.constants.PortalConstants;
import com.usermgmt.dto.UserDetailsDTO;

@Component
public class UserMgmtUtil {

	public boolean validateUserDetails(UserDetailsDTO userDetails) {
		boolean validated = true;
		if(null != userDetails) {
			String emailId = userDetails.getEmailId();
			String password = userDetails.getPassword();
			String fullName = userDetails.getFullname();
			String phoneNumber = userDetails.getPhoneNumber();
			String jobTitle = userDetails.getJobTitle();
			String department = userDetails.getDepartment();
			
			if(null == fullName || fullName.trim().isEmpty()) {
				validated = false;
			} else if(null == emailId || emailId.trim().isEmpty() || !emailId.matches(PortalConstants.VALID_EMAIL_ADDRESS_REGEX)) {
				validated = false;
			} else if(null == password || password.trim().isEmpty() || !password.matches(PortalConstants.VALID_PASSWORD_REGEX)) {
				validated = false;
			} else if(null == department || department.trim().isEmpty()) {
				validated = false;
			} else if(null == jobTitle || jobTitle.trim().isEmpty()) {
				validated = false;
			}  else if(null == phoneNumber || phoneNumber.trim().isEmpty() || !phoneNumber.matches(PortalConstants.VALID_PHONE_NUMBER_REGEX)) {
				validated = false;
			} 
		} else {
			validated = false;
		}
		return validated;
	}
}
