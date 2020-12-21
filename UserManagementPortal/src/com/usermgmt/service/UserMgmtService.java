package com.usermgmt.service;

import com.usermgmt.dto.UserDetailsDTO;

/**
 * @author hansikasingh
 *
 */
public interface UserMgmtService {

	UserDetailsDTO addNewUser(UserDetailsDTO userDetails);

	boolean authenticateUser(String emailId, String password);

	UserDetailsDTO fetchUserDetails(String emailId);

	boolean checkIsExistingUserByEmail(String email);

	UserDetailsDTO updateUser(UserDetailsDTO userDetails);

	boolean checkIsEmailUpdated(String emailId);

	boolean deleteUser(String email);

}