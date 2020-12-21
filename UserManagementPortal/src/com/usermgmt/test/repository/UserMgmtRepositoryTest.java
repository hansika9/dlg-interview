package com.usermgmt.test.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.usermgmt.model.User;
import com.usermgmt.model.UserAccount;
import com.usermgmt.repository.UserMgmtRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:/springtest-servlet.xml") 
public class UserMgmtRepositoryTest {
	
	@Autowired
	private UserMgmtRepository userRepository;

	@Mock
	private SessionFactory mockedSessionFactory;
	
	@Mock
	private Session mockedSession;
	
	@Mock
	private Transaction mockedTransaction;
	
	@Mock
	private Query mockedQuery;
	
	private UserMgmtRepository spyRepo;
	
	@BeforeEach
	public void setUp() {
		String email = "test@service.com";
	    mockedSessionFactory =  Mockito.mock(SessionFactory.class);
	    mockedSession = Mockito.mock(Session.class);
	    mockedTransaction = Mockito.mock(Transaction.class);
	    mockedQuery = Mockito.mock(Query.class);
	    Mockito.when(mockedSessionFactory.openSession()).thenReturn(mockedSession);
	    Mockito.when(mockedSession.beginTransaction()).thenReturn(mockedTransaction);
	    Mockito.when(mockedSession.createQuery(ArgumentMatchers.anyString())).thenReturn(mockedQuery);
	    Mockito.when(mockedQuery.setParameter("email", email)).thenReturn(mockedQuery);
	    ReflectionTestUtils.setField(userRepository, "sessionFactory", mockedSessionFactory);
	    spyRepo = Mockito.spy(userRepository);
	}
	
	@Test
	void testupdateUserDetailsFail() {
		String email = "test@service.com";
		User userToUpdate = Mockito.mock(User.class);
		UserAccount userAccToUpdate = Mockito.mock(UserAccount.class);
		Mockito.when(spyRepo.getUserByEmail(email)).thenReturn(null);
		User actualRespUser = userRepository.updateUserDetails(userAccToUpdate, userToUpdate);
		Assertions.assertNull(actualRespUser);
	}
}
