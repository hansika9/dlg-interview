package com.usermgmt.service.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.StringUtils;
import com.usermgmt.dto.UserDetailsDTO;
import com.usermgmt.mapper.UserMapper;
import com.usermgmt.model.User;
import com.usermgmt.model.UserAccount;
import com.usermgmt.repository.UserMgmtRepository;
import com.usermgmt.service.UserMgmtService;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	private static final Logger LOG = Logger.getLogger(UserMgmtServiceImpl.class);
	
	@Autowired
	private UserMgmtRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetailsDTO addNewUser(UserDetailsDTO userDetails) {
		UserDetailsDTO createdUserDto = null;
		if(null != userDetails) {
			User userObj = userMapper.mapUserDetailsDtoToUser(userDetails);
			UserAccount userAccObj = userMapper.mapUserDetailsDtoToUserAccount(userDetails);
			User createdUser = userRepository.addNewUser(userAccObj, userObj);
			if(null != createdUser) {
				createdUserDto = userMapper.mapUserToUserDetailsDTO(createdUser);
			}
		}
		return createdUserDto;
	}
	
	@Override
	public boolean checkIsExistingUserByEmail(String email) {
		if(!StringUtils.isNullOrEmpty(email) && null != userRepository.getUserAccountByEmail(email)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean authenticateUser(String emailId, String password) {
		boolean authenticated = false;
		if(null != emailId && null != password) {
			UserAccount userAcc = userRepository.getUserAccountByEmail(emailId);
			if(null != userAcc && userAcc.getPassword().equals(password)) {
				authenticated = true;
			}
		}
		return authenticated;
	}

	@Override
	public UserDetailsDTO fetchUserDetails(String emailId) {
		UserDetailsDTO userDetails = null;
		if(null != emailId) {
			User userObj = userRepository.getUserByEmail(emailId);
			userDetails = userMapper.mapUserToUserDetailsDTO(userObj);
		}
		return userDetails;
	}
	
	@Override
	public UserDetailsDTO updateUser(UserDetailsDTO userDetails) {
		UserDetailsDTO updatedUserDetails = null;
		UserAccount userAccToUpdate = userMapper.mapUserDetailsDtoToUserAccount(userDetails);
		User userToUpdate = userMapper.mapUserDetailsDtoToUser(userDetails);
		User updatedUser = userRepository.updateUserDetails(userAccToUpdate, userToUpdate);
		if(null != updatedUser) {
			updatedUserDetails = userMapper.mapUserToUserDetailsDTO(updatedUser);
		}
		return updatedUserDetails;
	}
	
	@Override
	public boolean checkIsEmailUpdated(String emailId) {
		boolean isEmailUpdated = false;
		UserAccount userAcc = userRepository.getUserAccountByEmail(emailId);
		if(null == userAcc) {
			isEmailUpdated = true;
		}
		return isEmailUpdated;
	}
	
	@Override
	public boolean deleteUser(String email) {
		boolean isDeleted = false;
		if(!StringUtils.isNullOrEmpty(email)) {
			isDeleted = userRepository.deleteUser(email);
		}
		return isDeleted;
	}
}
