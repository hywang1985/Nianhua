package com.hywang.timeline.persistence.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hywang.timeline.entity.LogonValidationInfo;
import com.hywang.timeline.entity.User;
import com.hywang.timeline.persistence.dao.UserDAO;


@Component("userDao")
public class UserDAOImpl extends HibernateGenericDaoImpl implements UserDAO {

    private static String getUserByNameHQL = "from User as user where user.userName = ?";
    
    private static String deleteByIDHQL="delete from User as user where user.id=?";
    
    private static String findLogonInfoHQL="from LogonValidationInfo as info where info.userName=? and info.sessionid=?";
    
    private static String deleteLogonInfoHQL="delete from LogonValidationInfo as info where info.userName=? and info.sessionid=?";
    
    public void deleteUserByID(int userId) throws DataAccessException {
    	Session session =sessionFactory.getCurrentSession();
    	Transaction tx=	session.getTransaction();
    	tx.begin();
    	Query q= session.createQuery(deleteByIDHQL);
    	q.setInteger(0, userId);
    	q.executeUpdate();
    	tx.commit();
    }
    

    @SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
        return findByExample(new User());
    }

    public User getUserByID(int id) {
    	return (User)findById(User.class.getName(), id);
    }
    
    
    public User getUserByName(String userName) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx=	session.getTransaction();
		tx.begin();
		Query q = session.createQuery(getUserByNameHQL);
		q.setString(0, userName);
		User user=(User)q.uniqueResult();
		tx.commit();
		return user;
    }

    public void addUser(String userName, String pwd, String email, String firstName, String lastName) throws DataAccessException {
    	User user = new User();
    	user.setUserName(userName);
    	user.setUserPwd(pwd);
    	user.setEmail(email);
    	user.setFirstName(firstName);
    	user.setLastName(lastName);
    	addUser(user);
    }
    
    public void addUser(User user) throws DataAccessException {
              save(user);
    }
    
    public boolean getAutoLoginState(String uname, String sessionid) {
    	
    	
    	return findLogonInfo(uname,sessionid)!=null;
    }
    
    public LogonValidationInfo findLogonInfo(String uname,String sessionid){
    	Session session = sessionFactory.getCurrentSession();
		Transaction tx=	session.getTransaction();
		tx.begin();
		Query q = session.createQuery(findLogonInfoHQL);
		q.setString(0, uname);
		q.setString(1, sessionid);
		LogonValidationInfo info=(LogonValidationInfo)q.uniqueResult();
		tx.commit();
    	return info;
    }

	public void insertUserLogonValidateInfo(LogonValidationInfo logonInfo)
			throws DataAccessException {
		save(logonInfo);
	}

	public void deleteAutoLoginState(String uname, String sessionid)
			throws DataAccessException {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx=	session.getTransaction();
		tx.begin();
		Query q = session.createQuery(deleteLogonInfoHQL);
		q.setString(0, uname);
		q.setString(1, sessionid);
		q.executeUpdate();
		tx.commit();
	}

   

    

   
}
