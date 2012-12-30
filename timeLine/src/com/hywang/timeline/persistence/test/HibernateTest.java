package com.hywang.timeline.persistence.test;

import static org.hibernate.criterion.Example.create;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateTest {

	public static void main(String[] args) {

		TestObject to = new TestObject();
		to.setName("Wang");

		Configuration cfg = new Configuration();
		cfg.addClass(TestObject.class);
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.saveOrUpdate(to); // create or update the to
		transaction.commit();
		Query q = session.createQuery("from TestObject where id=?");
		q.setInteger(0, 1);
//		List<TestObject> results = (List<TestObject>) session
//		   .createCriteria(TestObject.class.getName()).add(create(new TestObject())).list();
		List<TestObject> results=q.list();
		for(TestObject o:results){
			System.out.println(o.getName());
		}
		// .addClass(org.hibernate.auction.Item.class)
		// .addClass(org.hibernate.auction.Bid.class)
		// .setProperty("hibernate.dialect",
		// "org.hibernate.dialect.MySQLInnoDBDialect")
		// .setProperty("hibernate.connection.datasource",
		// "java:comp/env/jdbc/test")
		// .setProperty("hibernate.order_updates", "true");
	}
}
