package com.usermgmt.repository;

import com.usermgmt.model.User;
import com.usermgmt.model.UserAccount;

public interface UserMgmtRepository {

	UserAccount getUserAccountByEmail(String userEmail);

	User addNewUser(UserAccount userAccountObj, User userObj);

	User getUserByEmail(String email);

	User updateUserDetails(UserAccount userAccountObj, User userObj);

	boolean deleteUser(String email);

}