package com.hywang.timeline.persistence.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hywang.timeline.persistence.dao.HibernateDAO;
import com.hywang.timeline.persistence.exception.DataAccessRuntimeException;


@Component
public class HibernateGenericDaoImpl implements HibernateDAO {

	
	@Autowired
	protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Serializable findById(String className, Serializable id)
	   throws DataAccessRuntimeException {
		// Get session and perform query
		Session session = sessionFactory.getCurrentSession();
		Serializable instance = (Serializable) session.get(className, id);
		if (instance == null) {
			throw new DataAccessRuntimeException("No instance matching " + id + " found.");
		}
		return instance;
	}
	

	@SuppressWarnings("rawtypes")
	public List findByExample(Serializable example) throws DataAccessRuntimeException {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction =session.getTransaction();
		transaction.begin();
		Criteria createCriteria = session
		   .createCriteria(example.getClass().getName());
		@SuppressWarnings("unchecked")
		List<Serializable> results = (List<Serializable>) createCriteria.list();
		transaction.commit();
		return results;
	}

	public Serializable save(Serializable instance) throws DataAccessRuntimeException {
		Session session = sessionFactory.getCurrentSession();
		Serializable id = session.save(instance);
		session.flush();
		return instance;
	}

	public Serializable update(Serializable instance) throws DataAccessRuntimeException {
		Session session = sessionFactory.getCurrentSession();
		Serializable instance2 = (Serializable) session.merge(instance);
		session.flush();

		return instance2;
	}

	public boolean delete(Serializable instance) throws DataAccessRuntimeException {
		Session session = sessionFactory.getCurrentSession();
		session.delete(instance);
		session.flush();
		return true;

	}

}
