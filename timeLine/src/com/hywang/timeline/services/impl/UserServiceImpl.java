package com.hywang.timeline.services.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hywang.timeline.entity.LogonValidationInfo;
import com.hywang.timeline.entity.User;
import com.hywang.timeline.services.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private static String getUserByNameHQL = "from User as user where user.userName = ?";

	private static String deleteByIDHQL = "delete from User as user where user.id=?";

	private static String findLogonInfoHQL = "from LogonValidationInfo as info where info.userName=? and info.sessionid=?";

	private static String deleteLogonInfoHQL = "delete from LogonValidationInfo as info where info.userName=? and info.sessionid=?";

	private HibernateDaoSupport dao;

	public HibernateDaoSupport getDao() {
		return dao;
	}

	@Autowired
	public void setDao(HibernateDaoSupport dao) {
		this.dao = dao;
	}

	public void deleteUserByID(int userId) throws DataAccessException {
		this.dao.getHibernateTemplate().bulkUpdate(deleteByIDHQL, userId);
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return this.dao.getHibernateTemplate().findByExample(User.class);
	}

	public User getUserByID(int id,boolean initChildren) {
		User user = this.dao.getHibernateTemplate().load(User.class, id);
		if(user!=null && initChildren){
			initlinazeObject(user.getNodes());
		}
		return user;
	}

	public User getUserByName(String userName ,boolean initChildren) {
		User user = null;
		List results = this.dao.getHibernateTemplate().find(getUserByNameHQL,
				userName);
		if (results != null && !results.isEmpty()) {
			user = (User) results.get(0);
		}
		if(user!=null && initChildren){
			initlinazeObject(user.getNodes());
		}
		return user;
	}

	public void addUser(String userName, String pwd, String email,
			String firstName, String lastName) throws DataAccessException {
		User user = new User();
		user.setUserName(userName);
		user.setUserPwd(pwd);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		addUser(user);
	}

	public void addUser(User user) throws DataAccessException {
		this.dao.getHibernateTemplate().save(user);
	}

	public boolean getAutoLoginState(String uname, String sessionid) {

		return findLogonInfo(uname, sessionid) != null;
	}

	public LogonValidationInfo findLogonInfo(String uname, String sessionid) {
		LogonValidationInfo result = null;
		Object[] params = { uname, sessionid };
		List results = this.dao.getHibernateTemplate().find(findLogonInfoHQL,
				params);
		if (results != null && !results.isEmpty()) {
			result = (LogonValidationInfo) results.get(0);
		}
		return result;
	}

	public void insertUserLogonValidateInfo(LogonValidationInfo logonInfo)
			throws DataAccessException {
		this.dao.getHibernateTemplate().save(logonInfo);
	}

	public void deleteAutoLoginState(String uname, String sessionid)
			throws DataAccessException {
		Object[] params = { uname, sessionid };
		this.dao.getHibernateTemplate().bulkUpdate(deleteLogonInfoHQL, params);
	}

	@Override
	public void initlinazeObject(Object proxy) {
			Hibernate.initialize(proxy);  //To solve the lazy-load problem
	}
	
	

}
