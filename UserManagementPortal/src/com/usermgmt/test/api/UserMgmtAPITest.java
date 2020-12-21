package com.usermgmt.test.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.usermgmt.api.UserMgmtAPI;
import com.usermgmt.dto.UserDetailsDTO;
import com.usermgmt.service.UserMgmtService;
import com.usermgmt.util.UserMgmtUtil;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:/springtest-servlet.xml")
public class UserMgmtAPITest {

	@Mock
	private UserMgmtService userMgmtService;

	@Mock
	private UserMgmtUtil userMgmtUtil;
	
	@Autowired
	private UserMgmtAPI userMgmtApi;
	
	private static final String TEST_AUTH_TOKEN = "Basic aGFuc2lrYUBnbWFpbC5jb206SGFuc2lrYUA5";
	
	@BeforeEach
	public void setUp() {
		userMgmtService = Mockito.mock(UserMgmtService.class);
		userMgmtUtil = Mockito.mock(UserMgmtUtil.class);
		ReflectionTestUtils.setField(userMgmtApi, "userMgmtService", userMgmtService);
		ReflectionTestUtils.setField(userMgmtApi, "userMgmtUtil", userMgmtUtil);
	}
	
	@Test
	@DisplayName("Test Fetch User Details Success")
	void testFetchUserDetailsSuccess() {
		UserDetailsDTO expectedUserDetails = new UserDetailsDTO();
		Mockito.when(userMgmtService.authenticateUser(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(true);
		Mockito.when(userMgmtService.fetchUserDetails(ArgumentMatchers.anyString())).thenReturn(expectedUserDetails);
		ResponseEntity<Object> actualResponse = userMgmtApi.fetchUserDetails(TEST_AUTH_TOKEN);
		Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
	}
}
