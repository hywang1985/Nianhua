package com.hywang.timeline.persistence.dao.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.entity.User;
import com.hywang.timeline.persistence.dao.TimlineNodeDAO;


@Component
public class TimelineNodeDAOImpl extends HibernateGenericDaoImpl implements TimlineNodeDAO {

	private static String deleteQuery="delete TimeLineNode as node where node.ID=?";
	

    public boolean deleteNode(TimeLineNode node)  throws DataAccessException{
        return delete(node);
    }
    
    @Override
	public boolean deleteNodeById(int nodeId) throws DataAccessException {
    	Session session = sessionFactory.getCurrentSession();
    	Transaction tx=	session.getTransaction();
    	tx.begin();
    	Query q=session.createQuery(deleteQuery);
    	q.setInteger(0, nodeId);
    	int rowEffected =q.executeUpdate();
    	tx.commit();
		return rowEffected>0;
	}
    

    @SuppressWarnings("unchecked")
	public List<TimeLineNode> getAllNodes() throws DataAccessException {
    	  List<TimeLineNode> nodes =  (List<TimeLineNode>)this.findByExample(new TimeLineNode());
        return nodes;
    }
    

    public TimeLineNode getNodeByID(int nodeId) throws DataAccessException {
        return (TimeLineNode) findById(TimeLineNode.class.getName(), nodeId);
    }
    
	@Override
	public Set<TimeLineNode> getNodesByUser(User user) throws DataAccessException {
		return user.getNodes(); //lazy=false,so can get it directly, problem is once data is too much,the memory cost is too much.
	}

    
    public int addNode(TimeLineNode node) throws DataAccessException {
    		save(node);
    		return node.getID();
    }

    private String addQuote(String value) {
       return "\'"+value+"\'";
    }

    private String formatDate(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String datetime = sdf.format(date);
        return datetime;
    }
    

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
