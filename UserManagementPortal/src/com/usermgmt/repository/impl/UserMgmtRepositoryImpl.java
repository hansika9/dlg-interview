package com.usermgmt.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.cj.util.StringUtils;
import com.usermgmt.model.User;
import com.usermgmt.model.UserAccount;
import com.usermgmt.repository.UserMgmtRepository;

@Repository
public class UserMgmtRepositoryImpl implements UserMgmtRepository {

	private static final Logger LOG = Logger.getLogger(UserMgmtRepositoryImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public UserAccount getUserAccountByEmail(String userEmail) {
		UserAccount userAccount = null;
		Session session = sessionFactory.openSession();
		try {
			if(!StringUtils.isNullOrEmpty(userEmail)) {
				String getUserAccQuery = "from UserAccount userAcc where userAcc.email = :userEmail";
				Query query = session.createQuery(getUserAccQuery);
				query.setParameter("userEmail", userEmail);
				userAccount = (UserAccount) query.uniqueResult();
			}
		} catch (ClassCastException e) {
			LOG.error("method: getUserAccount() : ", e);
		}
		finally {
			session.close();
		}
		return userAccount;
	}
	
	@Override
	public User addNewUser(UserAccount userAccountObj, User userObj) {
		User createdUser = null;
		if(null != userAccountObj && null != userObj) {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(userAccountObj);
			session.save(userObj);
			session.getTransaction().commit();
			createdUser = userObj;
			session.close();
		}
		return createdUser;
	}
	
	@Override
	public User getUserByEmail(String email) {
		User user = null;
		Session session = sessionFactory.openSession();
		try {
			if(!StringUtils.isNullOrEmpty(email)) {
				String getUserQuery = "from User user where user.emailId = :email";
				Query query = session.createQuery(getUserQuery);
				query.setParameter("email", email);
				user = (User) query.uniqueResult();
			}
		} catch (ClassCastException e) {
			LOG.error("getUserByEmail method: ", e);
		}
		finally {
			session.close();
		}
		return user;
	}
	
	@Override
	public User updateUserDetails(UserAccount userAccountObj, User userObj) {
		User updatedUser = null;
		User originalUser = getUserByEmail(userAccountObj.getEmail());
		if(null != userAccountObj && null != userObj && null != originalUser) {
			userObj.setUserId(originalUser.getUserId());
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(userAccountObj);
			session.update(userObj);
			session.getTransaction().commit();
			updatedUser = userObj;
			session.close();
		}
		return updatedUser;
	}
	
	@Override
	public boolean deleteUser(String email) {
		boolean isDeleted = false;
		if(!StringUtils.isNullOrEmpty(email)) {
			UserAccount accObj = getUserAccountByEmail(email);
			User userObj = getUserByEmail(email);
			if(null != accObj && null != userObj) {
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				session.delete(userObj);
				session.delete(accObj);
				session.getTransaction().commit();
				isDeleted = true;
				session.close();
			}
		}
		return isDeleted;
	}
}
