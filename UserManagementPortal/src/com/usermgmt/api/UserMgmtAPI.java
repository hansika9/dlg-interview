package com.usermgmt.api;

import java.util.Base64;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.util.StringUtils;
import com.usermgmt.constants.PortalConstants;
import com.usermgmt.dto.UserDetailsDTO;
import com.usermgmt.service.UserMgmtService;
import com.usermgmt.util.UserMgmtUtil;

@RestController
@RequestMapping("/user")
public class UserMgmtAPI {

	private static final Logger LOG = Logger.getLogger(UserMgmtAPI.class);

	@Autowired
	private UserMgmtService userMgmtService;

	@Autowired
	private UserMgmtUtil userMgmtUtil;

	@GetMapping("/get")
	public ResponseEntity<Object> fetchUserDetails(@RequestHeader("Authorization") String authToken) {
		ResponseEntity<Object> apiResponse = null;
		boolean authFailure = true;
		if (!StringUtils.isNullOrEmpty(authToken)) {
			try {
				String[] decodedToken = decodeAuthToken(authToken);
				if(null != decodedToken && decodedToken.length == 2) {
					String email = decodedToken[0];
					String password = decodedToken[1];
					if (userMgmtService.authenticateUser(email, password)) {
						authFailure = false;
						UserDetailsDTO userDetailsDto = userMgmtService.fetchUserDetails(email);
						if(null != userDetailsDto) {
							apiResponse = ResponseEntity.ok(userDetailsDto);
							LOG.info("Request from: " + email + "Response: " + userDetailsDto.toString());
						} else {
							apiResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
									.body("Some error has occurred at the server. Please try again or contact support.");
							LOG.error("Get UserDetails operation failed. Request from: " + email);
						}
					}
				}
			} catch (IllegalArgumentException e) {
				LOG.error("Exception occured while decoding auth token: " + authToken);
			}
		}
		if (authFailure) {
			apiResponse = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials. Please try again.");
			LOG.info("User authentication failed for token:" + authToken);
		} else if(null == apiResponse){
			apiResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Some error has occurred at the server. Please try again or contact support.");
			LOG.error("Get UserDetails operation failed. Request token:" + authToken);
		}
		return apiResponse;
	}

	@PostMapping("/add")
	public ResponseEntity<Object> addUserDetails(@RequestBody UserDetailsDTO userDetailsRequest) {
		ResponseEntity<Object> apiResponse = null;
		if (null != userDetailsRequest) {
			if(!userMgmtService.checkIsExistingUserByEmail(userDetailsRequest.getEmailId())) {
				if(userMgmtUtil.validateUserDetails(userDetailsRequest)) {
					UserDetailsDTO userDetailsResponse = userMgmtService.addNewUser(userDetailsRequest);
					if(null != userDetailsResponse) {
						apiResponse = ResponseEntity.ok().body(userDetailsResponse);
						LOG.info("User created successfully! " + userDetailsResponse.toString());
					}
				} else{
					apiResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Information is incorrect/incomplete.");
					LOG.info("User not created. User information not sufficient. " + userDetailsRequest.toString());
				}
			} else{
				apiResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("User with this emailId already exists.");
				LOG.info("User not created. EmailId already exists." + userDetailsRequest.toString());
			}
		} 
		if (null == apiResponse) {
			apiResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Some error has occurred at the server. Please try again or contact support.");
			LOG.error("Add User operation failed for the following user details: " + userDetailsRequest.toString());
		}
		return apiResponse;
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateUserDetails(@RequestHeader("Authorization") String authToken, @RequestBody UserDetailsDTO userDetails) {
		ResponseEntity<Object> apiResponse = null;
		boolean authFailure = true;
		if(!StringUtils.isNullOrEmpty(authToken)) {
			String[] decodedToken = decodeAuthToken(authToken);
			if(null != decodedToken && decodedToken.length == 2) {
				String email = decodedToken[0];
				String password = decodedToken[1];
				if(userMgmtService.authenticateUser(email, password)) {
					authFailure = false;
					if(userMgmtUtil.validateUserDetails(userDetails)) {
						if(email.equals(userDetails.getEmailId())) {
							UserDetailsDTO updatedUserDetails = userMgmtService.updateUser(userDetails);
							if(null != updatedUserDetails) {
								apiResponse = ResponseEntity.ok(updatedUserDetails);
								LOG.info("Request from: "+ email +" User Details updated successfully. " + updatedUserDetails.toString());
							}
						} else {
							apiResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Email cannot be updated.");
							LOG.info("Request from: "+ email +" User not updated as email cannot be updated. " + userDetails.toString());
						}
					} else {
						apiResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Information is incorrect/incomplete.");
						LOG.info("Request from: "+ email +" User not updated as invalid User Details received. " + userDetails.toString());
					}
				}
			}
		}
		if(authFailure) {
			apiResponse = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials. Please try again.");
			LOG.info("Request from: "+ authToken +"User authentication failed.");
		}
		if(null == apiResponse) {
			apiResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Some error has occurred at the server. Please try again or contact support.");
			LOG.error("Request from: "+ authToken +"User not updated due to internal server error. " + userDetails.toString());
		}
		
		return apiResponse;
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteUser(@RequestHeader("Authorization") String authToken) {
		ResponseEntity<Object> apiResponse = null;
		boolean authFailure = true;
		if(!StringUtils.isNullOrEmpty(authToken)) {
			String[] decodedTokens = decodeAuthToken(authToken);
			if(null != decodedTokens && decodedTokens.length == 2) {
				String email = decodedTokens[0];
				String password = decodedTokens[1];
				if(userMgmtService.authenticateUser(email, password)) {
					authFailure = false;
					if(userMgmtService.deleteUser(email)) {
						apiResponse = ResponseEntity.ok("User deleted successfully.");
						LOG.info("Request from: " + email + "User deleted successfully.");
					}
				}
			}
		}
		if(authFailure) {
			apiResponse = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials. Please try again.");
			LOG.info("Request from: " + authToken +" User authentication failed.");
		}
		if(null == apiResponse) {
			apiResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Some error has occurred at the server. Please try again or contact support.");
			LOG.error("Request from: " + authToken +" User not deleted due to internal server error.");
		}
		return apiResponse;
	}

	private String[] decodeAuthToken(String authToken) throws IllegalArgumentException {
		String[] tokens = null;
		if (!StringUtils.isNullOrEmpty(authToken)) {
			authToken = authToken.replaceFirst(PortalConstants.BASIC_AUTH_HEADER_PREFIX, PortalConstants.EMPTY_STRING);
			byte[] decodedtoken = Base64.getDecoder().decode(authToken);
			tokens = (new String(decodedtoken)).split(":");
		}
		return tokens;
	}
}
