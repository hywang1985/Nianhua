package com.hywang.timeline.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.hywang.timeline.dao.AbstractDatabaseDAO;
import com.hywang.timeline.dao.TimlineNodeDAO;
import com.hywang.timeline.entity.Category;
import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.entity.User;

public class TimelineNodeDAOImpl extends AbstractDatabaseDAO implements TimlineNodeDAO {

    private static String allNodesSql = "select * from `timeline`.`timelinenode`;";

    private static String nodesByCateSql = "select * from `timeline`.`timelinenode` where categoryID = ";
    
    private static String addNodeSql= "insert into `timeline`.`timelinenode` (`starttime`, `endtime`, `header`, `article`, `tags`, `media`, `credit`, `caption`,`bgrimg`) " +
    		"VALUES";

    private static String deleteNodeSql = "delete from `timeline`.`timelinenode` WHERE `id`= ";



    public boolean delNodeByID(int id)  throws Exception{
    	String deleteSql = deleteNodeSql+ "'"+id+"'";
        Connection conn = util.getConnection();
        int result= util.executeUpdate(conn, deleteSql); //return 1 if delete success,or will be 0
        return result==1;
    }
    
	@Override
	public void deleteNodeRelation(int userid, int nodeid) throws Exception {
		String deleteRelationSql= "DELETE FROM `timeline`.`noderelation` WHERE `userid`="+userid+" and `nodeid`="+nodeid;
		Connection conn = util.getConnection();
		
		try {
			util.executeUpdate(conn, deleteRelationSql);
		}  catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            util.closeConnection(conn);
        }
	}

    public List<TimeLineNode> getAllNodes() throws Exception {

        Connection conn = util.getConnection();
        ResultSet rs = util.executeQuery(conn, allNodesSql);
        List<TimeLineNode> nodes = new ArrayList<TimeLineNode>();
        try {
            while (rs.next()) {
                int nodeID = rs.getInt(1);
                Date startDate = rs.getDate(2);
                Date endDate = rs.getDate(3);
                String header = rs.getString(4);
                String article = rs.getString(5);
                int titleSlide = rs.getInt(6);
                boolean isStart = (titleSlide == 1);
                String tags = rs.getString(7);
                int category_refID = rs.getInt(8);
                String media=rs.getString(9);
                String credit=rs.getString(10);
                String caption=rs.getString(11);
                String bgrImg=rs.getString(12);
                /*
                 * currently use setter,cuz perhaps migrate to hibernate which support standard java bean specification
                 */
                TimeLineNode tlNode = new TimeLineNode();
                tlNode.setID(nodeID);
                tlNode.setStartDate(startDate);
                tlNode.setEndDate(endDate);
                tlNode.setHeadline(header);
                tlNode.setText(article);
                tlNode.setStartNode(isStart);
                tlNode.setTags(tags);
                tlNode.setMedia(media);
                tlNode.setCredit(credit);
                tlNode.setCaption(caption);
                tlNode.setBgrImg(bgrImg);
                Category cate = daoFactory.createCategoryDAO().getCategoryByID(category_refID);
                User author = daoFactory.createUserDAO().getAuthor(nodeID);
                tlNode.setAuthor(author);
                tlNode.setCate(cate);
                nodes.add(tlNode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
        	rs.close();
            util.closeConnection(conn);
        }
        return nodes;
    }
    

    public TimeLineNode getNodeByID(int id) throws Exception {
    	String sql = "select * from `timeline`.`timelinenode` where id="+id;
    	TimeLineNode toReturn =null;
    	Connection conn = util.getConnection();
    	ResultSet rs = util.executeQuery(conn, sql);
    	 try {
	            while (rs.next()) {
	            	 int nodeID = rs.getInt(1);
	                 Date startDate = rs.getDate(2);
	                 Date endDate = rs.getDate(3);
	                 String header = rs.getString(4);
	                 String article = rs.getString(5);
	                 int titleSlide = rs.getInt(6);
	                 boolean isStart = (titleSlide == 1);
	                 String tags = rs.getString(7);
	                 int category_refID = rs.getInt(8);
	                 String media=rs.getString(9);
	                 String credit=rs.getString(10);
	                 String caption=rs.getString(11);
	                 String bgrImg=rs.getString(12);
	                 /*
	                  * currently use setter,cuz perhaps migrate to hibernate which support standard java bean specification
	                  */
	                 toReturn = new TimeLineNode();
	                 toReturn.setID(nodeID);
	                 toReturn.setStartDate(startDate);
	                 toReturn.setEndDate(endDate);
	                 toReturn.setHeadline(header);
	                 toReturn.setText(article);
	                 toReturn.setStartNode(isStart);
	                 toReturn.setTags(tags);
	                 toReturn.setMedia(media);
	                 toReturn.setCredit(credit);
	                 toReturn.setCaption(caption);
	                 toReturn.setBgrImg(bgrImg);
	                 User author = daoFactory.createUserDAO().getAuthor(nodeID);
	                 toReturn.setAuthor(author);
	                 break;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	        } finally {
	        	rs.close();
	            util.closeConnection(conn);
	        }
        return toReturn;
    }
    
	@Override
	public List<TimeLineNode> getNodesByUserID(int userid) throws Exception {
		String sql = "select nodeid from `timeline`.`noderelation` where userid= "+userid;
		Connection conn = util.getConnection();
		List<TimeLineNode> userNodes = new ArrayList<TimeLineNode>();
		ResultSet rs = util.executeQuery(conn, sql);
		 try {
	            while (rs.next()) {
	            	int nodeID=rs.getInt(1);
	            	TimeLineNode node=getNodeByID(nodeID);
	            	if(node!=null){
	            		userNodes.add(node);
	            	}
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	        } finally {
	        	rs.close();
	            util.closeConnection(conn);
	        }
		return userNodes;
	}

    public List<TimeLineNode> getNodesByCategory(int cateID) {
        String nodesSql = nodesByCateSql + cateID + ";";
        Connection conn = util.getConnection();
        ResultSet rs = util.executeQuery(conn, nodesSql);
        List<TimeLineNode> nodes = new ArrayList<TimeLineNode>();
        try {
            while (rs.next()) {
                int nodeID = rs.getInt(1);
                Date startDate = rs.getDate(2);
                Date endDate = rs.getDate(3);
                String header = rs.getString(4);
                String article = rs.getString(5);
                int titleSlide = rs.getInt(6);
                boolean isStart = (titleSlide == 1);
                String tags = rs.getString(7);
                int category_refID = rs.getInt(8);
                String media=rs.getString(9);
                String credit=rs.getString(10);
                String caption=rs.getString(11);
                /*
                 * currently use setter,cuz perhaps migrate to hibernate which support standard java bean specification
                 */
                TimeLineNode tlNode = new TimeLineNode();
                tlNode.setID(nodeID);
                tlNode.setStartDate(startDate);
                tlNode.setEndDate(endDate);
                tlNode.setHeadline(header);
                tlNode.setText(article);
                tlNode.setStartNode(isStart);
                tlNode.setTags(tags);
                tlNode.setMedia(media);
                tlNode.setCredit(credit);
                tlNode.setCaption(caption);

                Category cate = daoFactory.createCategoryDAO().getCategoryByID(category_refID);
                tlNode.setCate(cate);
                nodes.add(tlNode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            util.closeConnection(conn);
        }
        return nodes;
    }
    
    public int addNode(TimeLineNode node) throws Exception {
        String startdate= formatDate(node.getStartDate()); 
        startdate=addQuote(startdate);
        String enddate= formatDate(node.getEndDate()); 
        enddate=addQuote(enddate);
        String  header =node.getHeadline();
        header=addQuote(header);
        String  article=node.getText();
        article=addQuote(article);
        String  tags =node.getTags();
        tags=addQuote(tags);
        String media=node.getMedia();
        media=addQuote(media);
        String credit=node.getCredit();
        credit=addQuote(credit);
        String caption=node.getCaption();
        caption=addQuote(caption);
        String bgrImg=node.getBgrImg();
        bgrImg=addQuote(bgrImg);
        String finalInsertSql =addNodeSql+"("+startdate+","+enddate+","+header+","+article+","+tags+","+media+","+credit+","+caption+","+bgrImg+
        ")";
        Connection conn = util.getConnection();
        try {
        	int infectId= util.executeUpdateWithRowID(conn, finalInsertSql);
        	return infectId;
       } catch (Exception e) {
         throw e;
       }finally{
    	   util.closeConnection(conn);
       }
        
    }

    private String addQuote(String value) {
       return "\'"+value+"\'";
    }

    private String formatDate(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String datetime = sdf.format(date);
        return datetime;
    }
    
    @Override
	public boolean updateNodeRelation(int userid, int nodeid) throws Exception {
    	String updateSql= "insert into `timeline`.`noderelation` (`userid`, `nodeid`) VALUES ("+userid+", "+nodeid+")";
    	 Connection conn = util.getConnection();
         try {
            util.executeUpdate(conn, updateSql);
        } catch (Exception e) {
          throw e;
        }finally{
     	   util.closeConnection(conn);
        }
		return false;
	}
    

    public static void main(String[] args) {
        /* all nodes */
		List<TimeLineNode> nodes;
		try {
			nodes = new TimelineNodeDAOImpl().getAllNodes();
			for (TimeLineNode node : nodes) {
				System.out.println("******************");
				System.out.println("ID: " + node.getID());
				System.out.println("StartTime: " + node.getStartDate());
				System.out.println("EndTime: " + node.getEndDate());
				System.out.println("Header: " + node.getHeadline());
				System.out.println("isStart: " + node.isStartNode());
				System.out.println("Article: " + node.getText());
				System.out.println("Tags: " + node.getTags());
				System.out.println("(");
				System.out.println("Figure media: " + node.getMedia());
				System.out.println("Figure credit: " + node.getCredit());
				System.out.println("Figure caption: " + node.getCaption());
				System.out.println("******************");
				System.out.println(")");
				System.out.println();
				System.out.println("(");
//				System.out.println("Cate ID: " + node.getCate().getId());
//				System.out.println("Cate Name: " + node.getCate().getName());
				System.out.println("******************");
				System.out.println(")");
				System.out.println();
				
				
			}
			boolean deleted =new TimelineNodeDAOImpl().delNodeByID(7);
			if(deleted){
				System.out.println("The timeline node 8 has been deleted sucessfully!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
      
//
//        /* nodes by category */
//        List<TimeLineNode> nodesBycate = new TimelineNodeDAOImpl().getNodesByCategory(1);
//        for (TimeLineNode node : nodesBycate) {
//            System.out.println("******************");
//            System.out.println("ID: " + node.getID());
//            System.out.println("StartTime: " + node.getStartDate());
//            System.out.println("EndTime: " + node.getEndDate());
//            System.out.println("Header: " + node.getHeadline());
//            System.out.println("isStart: " + node.isStartNode());
//            System.out.println("Article: " + node.getText());
//            System.out.println("Tags: " + node.getTags());
//            System.out.println("(");
//            System.out.println("Figure media: " + node.getMedia());
//            System.out.println("Figure credit: " + node.getCredit());
//            System.out.println("Figure caption: " + node.getCaption());
//            System.out.println("******************");
//            System.out.println(")");
//            System.out.println();
//            System.out.println("(");
//            System.out.println("Cate ID: " + node.getCate().getId());
//            System.out.println("Cate Name: " + node.getCate().getName());
//            System.out.println("******************");
//            System.out.println(")");
//            System.out.println();
//        }
//    }
        
    }





}
