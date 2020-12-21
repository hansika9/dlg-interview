package com.usermgmt.test.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.usermgmt.dto.UserDetailsDTO;
import com.usermgmt.mapper.UserMapper;
import com.usermgmt.model.User;
import com.usermgmt.model.UserAccount;
import com.usermgmt.repository.UserMgmtRepository;
import com.usermgmt.service.UserMgmtService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:/springtest-servlet.xml") 
public class UserMgmtServiceTest {

	@Autowired
	private UserMgmtService userMgmtService;

	@Mock
	private UserMgmtRepository userRepository;

	@Mock
	private UserMapper userMapper;
	
	@BeforeEach
	public void setUp() {
		userRepository = Mockito.mock(UserMgmtRepository.class);
		userMapper = Mockito.mock(UserMapper.class);
		ReflectionTestUtils.setField(userMgmtService, "userMapper", userMapper);
		ReflectionTestUtils.setField(userMgmtService, "userRepository", userRepository);
	}
	
	@Test
	@DisplayName("Test Add New User Success")
	void testAddNewUserSuccess() {
		User userObj = Mockito.mock(User.class);
		userObj.setEmailId("test@service.com");
		UserAccount userAccObj = Mockito.mock(UserAccount.class);
		UserDetailsDTO reqDetails = new UserDetailsDTO("Service Test", "test@service.com", "Pass@123", "+449876543210",
				"Test Department", "Test Job Title");
		UserDetailsDTO respExpected = new UserDetailsDTO("Service Test", "test@service.com", "", "+449876543210",
				"Test Department", "Test Job Title");
		Mockito.when(userMapper.mapUserDetailsDtoToUser(ArgumentMatchers.any(UserDetailsDTO.class))).thenReturn(userObj);
		Mockito.when(userMapper.mapUserDetailsDtoToUserAccount(ArgumentMatchers.any(UserDetailsDTO.class))).thenReturn(userAccObj);
		Mockito.when(userMapper.mapUserToUserDetailsDTO(ArgumentMatchers.any(User.class))).thenReturn(respExpected);
		Mockito.when(userRepository.addNewUser(ArgumentMatchers.any(UserAccount.class), ArgumentMatchers.any(User.class))).thenReturn(userObj);
		//doReturn(Optional.of(userObj)).when(userRepository).addNewUser(userAccObj, userObj);
		UserDetailsDTO respActual = userMgmtService.addNewUser(reqDetails);
		Assertions.assertEquals(respExpected.getEmailId(), respActual.getEmailId());
	}
	
	@Test
	@DisplayName("Test Add New User Fail")
	void testAddNewUserFail() {
		UserDetailsDTO reqDetails = null;
		UserDetailsDTO respActual = userMgmtService.addNewUser(reqDetails);
		Assertions.assertNull(respActual);
	}
	
	@Test
	@DisplayName("Test Check Is Existing User By Email Success")
	void testCheckIsExistingUserByEmailSuccess() {
		String reqEmail = "test@service.com";
		UserAccount userAccObj = Mockito.mock(UserAccount.class);
		Mockito.when(userRepository.getUserAccountByEmail(reqEmail)).thenReturn(userAccObj);
		boolean resp = userMgmtService.checkIsExistingUserByEmail(reqEmail);
		Assertions.assertTrue(resp);
	}
	
	@Test
	@DisplayName("Test Authenticate User Fail")
	void testAuthenticateUserFail() {
		String email = "abc@test.com";
		String password = "testPass";
		Mockito.when(userRepository.getUserAccountByEmail(email)).thenReturn(null);
		boolean response = userMgmtService.authenticateUser(email, password);
		Assertions.assertFalse(response);
	}
}
