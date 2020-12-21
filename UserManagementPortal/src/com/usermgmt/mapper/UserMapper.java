package com.usermgmt.mapper;

import org.springframework.stereotype.Component;

import com.usermgmt.constants.PortalConstants;
import com.usermgmt.dto.UserDetailsDTO;
import com.usermgmt.model.User;
import com.usermgmt.model.UserAccount;

@Component
public class UserMapper {

	public UserAccount mapUserDetailsDtoToUserAccount(UserDetailsDTO userDetailsDto) {
		UserAccount userAccount = null;
		if(null != userDetailsDto) {
			userAccount = new UserAccount();
			userAccount.setEmail(userDetailsDto.getEmailId());
			userAccount.setPassword(userDetailsDto.getPassword());
		} 
		return userAccount;		
	}
	
	public User mapUserDetailsDtoToUser(UserDetailsDTO userDetailsDto) {
		User user = null;
		if(null != userDetailsDto) {
			user = new User();
			user.setFullName(userDetailsDto.getFullname());
			user.setPhoneNumber(userDetailsDto.getPhoneNumber());
			user.setDepartment(userDetailsDto.getDepartment());
			user.setJobTitle(userDetailsDto.getJobTitle());
			user.setEmailId(userDetailsDto.getEmailId());
		}
		return user;
	}
	
	public UserDetailsDTO mapUserToUserDetailsDTO(User user) {
		UserDetailsDTO userDetailsDto = null;
		if(null != user) {
			userDetailsDto = new UserDetailsDTO();
			userDetailsDto.setEmailId(user.getEmailId());
			userDetailsDto.setFullname(user.getFullName());
			userDetailsDto.setPhoneNumber(user.getPhoneNumber());
			userDetailsDto.setDepartment(user.getDepartment());
			userDetailsDto.setJobTitle(user.getJobTitle());
			userDetailsDto.setPassword(PortalConstants.EMPTY_STRING);
		}
		return userDetailsDto;
	}
}
