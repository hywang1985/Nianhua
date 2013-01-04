package com.hywang.timeline.persistence.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;


@Repository
public class HibernateGenericDaoImpl extends HibernateDaoSupport{

	
	@Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory)   
    {   
  
        super.setSessionFactory(sessionFactory);   
    }

}
