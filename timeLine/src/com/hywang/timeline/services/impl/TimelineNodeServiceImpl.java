package com.hywang.timeline.services.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.entity.User;
import com.hywang.timeline.services.TimlineNodeService;


@Service
@Transactional
public class TimelineNodeServiceImpl implements TimlineNodeService{

	private static String deleteQuery="delete TimeLineNode as node where node.ID=?";
	
	private HibernateDaoSupport dao;

    public HibernateDaoSupport getDao() {
		return dao;
	}
    
    @Autowired
	public void setDao(HibernateDaoSupport dao) {
		this.dao = dao;
	}

	public void deleteNode(TimeLineNode node)  throws DataAccessException{
    	
    	this.dao.getHibernateTemplate().delete(node);
    }
    
    @Override
	public void deleteNodeById(int nodeId) throws DataAccessException {
    	this.dao.getHibernateTemplate().bulkUpdate(deleteQuery,nodeId);
	}
    

    @SuppressWarnings("unchecked")
	public List<TimeLineNode> getAllNodes() throws DataAccessException {
        return this.dao.getHibernateTemplate().loadAll(TimeLineNode.class);
    }
    

    public TimeLineNode getNodeByID(int nodeId,boolean needInitlized) throws DataAccessException {
        TimeLineNode pickedNode = this.dao.getHibernateTemplate().load(TimeLineNode.class, nodeId);
        if(pickedNode!=null && needInitlized){
        	initlinazeObject(pickedNode);
        }
		return pickedNode;
    }
    
    
    public int addNode(TimeLineNode node) throws DataAccessException {
		this.dao.getHibernateTemplate().save(node);
		return node.getID();
    }

	@Override
	public void initlinazeObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	@Override
	public void updateNode(TimeLineNode node) {
		this.dao.getHibernateTemplate().update(node);
	}

//    private String addQuote(String value) {
//       return "\'"+value+"\'";
//    }
//
//    private String formatDate(java.util.Date date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//        String datetime = sdf.format(date);
//        return datetime;
//    }
    

//    public static void main(String[] args) {
//        /* all nodes */
//		List<TimeLineNode> nodes;
//		try {
//			nodes = new TimelineNodeDAOImpl().getAllNodes();
//			for (TimeLineNode node : nodes) {
//				System.out.println("******************");
//				System.out.println("ID: " + node.getID());
//				System.out.println("StartTime: " + node.getStartDate());
//				System.out.println("EndTime: " + node.getEndDate());
//				System.out.println("Header: " + node.getHeadline());
//				System.out.println("isStart: " + node.isStartNode());
//				System.out.println("Article: " + node.getText());
//				System.out.println("Tags: " + node.getTags());
//				System.out.println("(");
//				System.out.println("Figure media: " + node.getMedia());
//				System.out.println("Figure credit: " + node.getCredit());
//				System.out.println("Figure caption: " + node.getCaption());
//				System.out.println("******************");
//				System.out.println(")");
//				System.out.println();
//				System.out.println("(");
////				System.out.println("Cate ID: " + node.getCate().getId());
////				System.out.println("Cate Name: " + node.getCate().getName());
//				System.out.println("******************");
//				System.out.println(")");
//				System.out.println();
//				
//				
//			}
//			boolean deleted =new TimelineNodeDAOImpl().delNodeByID(7);
//			if(deleted){
//				System.out.println("The timeline node 8 has been deleted sucessfully!");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//      
////
////        /* nodes by category */
////        List<TimeLineNode> nodesBycate = new TimelineNodeDAOImpl().getNodesByCategory(1);
////        for (TimeLineNode node : nodesBycate) {
////            System.out.println("******************");
////            System.out.println("ID: " + node.getID());
////            System.out.println("StartTime: " + node.getStartDate());
////            System.out.println("EndTime: " + node.getEndDate());
////            System.out.println("Header: " + node.getHeadline());
////            System.out.println("isStart: " + node.isStartNode());
////            System.out.println("Article: " + node.getText());
////            System.out.println("Tags: " + node.getTags());
////            System.out.println("(");
////            System.out.println("Figure media: " + node.getMedia());
////            System.out.println("Figure credit: " + node.getCredit());
////            System.out.println("Figure caption: " + node.getCaption());
////            System.out.println("******************");
////            System.out.println(")");
////            System.out.println();
////            System.out.println("(");
////            System.out.println("Cate ID: " + node.getCate().getId());
////            System.out.println("Cate Name: " + node.getCate().getName());
////            System.out.println("******************");
////            System.out.println(")");
////            System.out.println();
////        }
////    }
//        
//    }





}
