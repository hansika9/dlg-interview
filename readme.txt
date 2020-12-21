TECHNOLOGIES USED:-
	Java 1.8
	Spring MVC Framework - 5.1.5RELEASE
	Hibernate - 5.4.21.Final
	Apache Tomcat - 9
	MySQL - 5.7+
	Maven - 3.6.3

HOW TO RUN:-
	Step1: Create a sql schema in mySql.
	Step2: Crete following tables inside the above created schema-
	
			CREATE TABLE `user_account` (
			`email` varchar(100) NOT NULL,
			`password` varchar(50) NOT NULL,
			PRIMARY KEY (`email`));
			
			CREATE TABLE `user` (
		  `userId` int(11) NOT NULL AUTO_INCREMENT,
		  `name` varchar(100) NOT NULL,
		  `phoneNumber` varchar(15) DEFAULT NULL,
		  `department` varchar(50) DEFAULT NULL,
		  `jobTitle` varchar(50) DEFAULT NULL,
		  `emailId` varchar(100) NOT NULL,
		  PRIMARY KEY (`userId`),
		  KEY `fk_user_account_email_idx` (`emailId`),
		  CONSTRAINT `fk_user_useraccount_email` FOREIGN KEY (`emailId`) REFERENCES `user_account` (`email`));
		  
	Step3: Clone the project from the below repository:- 
			https://github.com/hansika9/dlg-interview
	Step4: Create a war file for this project by running the below command inside project folder, i.e. UserManagementPortal folder:-
			Command:- mvn clean package
	Step5: Copy the above created war file from UserManagementPortal/target folder into the webapps folder of your system's Apache Tomcat directory.
	Step6: Start the tomcat server.

IMPLEMENTED API's:-

1.	Request Type: POST
	URL: http://localhost:8080/UserManagementPortal/user/add
	Authorization - (None)
	RequestBody: (Sample)
		{
			"fullname" : "John Lewis",
			"emailId" : "john123@test.com",
			"password" : "John@12345",
			"phoneNumber" : "+9876543210",
			"department" : "Technology",
			"jobTitle" : "Developer"
		}
		
	Sample Response: 
		{
			"fullname" : "John Lewis",
			"emailId" : "john123@test.com",
			"password" : "",
			"phoneNumber" : "+9876543210",
			"department" : "Technology",
			"jobTitle" : "Developer"
		}
	

2. 	Request Type: GET
	URL: http://localhost:8080/UserManagementPortal/user/get
	Authorization - (Basic Auth)
		(for example)		username: john123@test.com
							password: John@12345
	Sample Response: 
		{
			"fullname" : "John Lewis",
			"emailId" : "john123@test.com",
			"password" : "",
			"phoneNumber" : "+9876543210",
			"department" : "Technology",
			"jobTitle" : "Developer"
		}
		
3. Request Type: PUT
	URL: http://localhost:8080/UserManagementPortal/user/update
	Authorization - (Basic Auth)
		(for example)		username: john123@test.com
							password: John@12345
	RequestBody: (Sample)
		{
			"fullname" : "John S. Lewis",
			"emailId" : "john123@test.com",
			"password" : "John@12345",
			"phoneNumber" : "09876543210",
			"department" : "Technology",
			"jobTitle" : "Senior Developer"
		}
		
	Sample Response: 
		{
			"fullname" : "John Lewis",
			"emailId" : "john123@test.com",
			"password" : "",
			"phoneNumber" : "+9876543210",
			"department" : "Technology",
			"jobTitle" : "Developer"
		}
		
4. Request Type: DELETE
	URL: http://localhost:8080/UserManagementPortal/user/delete
	Authorization - (Basic Auth)
		(for example)		username: john123@test.com
							password: John@12345
							
	Sample Response: User deleted successfully.
	

ASSUMPTIONS:-
1. A user cannot update its emailId. 
2. An empty string is returned as Password in get request made by valid user.
3. Assuming that UI is not required for this test project. Only implemented the APIs.