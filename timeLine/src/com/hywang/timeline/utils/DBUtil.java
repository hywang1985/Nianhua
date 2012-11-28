package com.hywang.timeline.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBUtil {

    private  String user;

    private  String pwd;

    private  String url;

    // private String catalog;

    private static DBUtil singleton = null;
    

    // DB propertis will be load only once,when first init the single instance
    private DBUtil() {
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            this.url = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "timeline" + "?" + "noDatetimeStringSync=true";
//            this.url = "jdbc:mysql://" + "113.11.199.100" + ":" + "3306" + "/" + "mysql" + "?" + "noDatetimeStringSync=true";
            this.user = "root";
            this.pwd = "root";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /* singleton,thread safe consideration */
    public synchronized static DBUtil getInstance() {
        if (singleton == null) {
            singleton = new DBUtil();
        }
        return singleton;
    }

    // public List<TimeLineNode> getAllTimeLineNodes() throws SQLException {
    // List<TimeLineNode> nodes =new ArrayList<TimeLineNode>();
    // String getTlNode="select * from timelinenode;";
    // this.conn =DriverManager.getConnection(url, user, pwd);
    // Statement stmt = this.conn.createStatement();
    // ResultSet rsNodes = stmt.executeQuery(getTlNode);
    // while(rsNodes.next()){
    // TimeLineNode tlNode = getNode(stmt, rsNodes);
    // nodes.add(tlNode);
    // }
    // return nodes;
    // }

    // private TimeLineNode getNode(Statement stmt, ResultSet rsNodes)
    // throws SQLException {
    // int nodeID =rsNodes.getInt(1);
    // Date startTime = rsNodes.getDate(2);
    // Date endTime=rsNodes.getDate(3);
    // String header=rsNodes.getString(4);
    // String article=rsNodes.getString(5);
    // int figure_refID=rsNodes.getInt(6);
    // /*currently use setter,cuz perhaps migrate to hibernate
    // which support standard java bean specification*/
    // TimeLineNode tlNode= new TimeLineNode();
    // tlNode.setID(nodeID);
    // tlNode.setStartTime(startTime);
    // tlNode.setEndTime(endTime);
    // tlNode.setHeader(header);
    // tlNode.setArticle(article);
    // // Figure figure = getFigure(stmt, figure_refID);
    // // tlNode.setFigure(figure);
    // return tlNode;
    // }

    // private Figure getFigure(Statement stmt, int figure_refID)
    // throws SQLException {
    // String getFigure ="select * from figure where id = "+figure_refID+ ";";
    // ResultSet rsFigure= stmt.executeQuery(getFigure);
    // Figure figure=new Figure();
    // while(rsFigure.next()){
    // int figureID=rsFigure.getInt(1);
    // String figureImgPath =rsFigure.getString(2);
    // String cite =rsFigure.getString(3);
    // String figCaption=rsFigure.getString(4);
    // String httpLink=rsFigure.getString(5);
    // figure.setID(figureID);
    // figure.setFigureImgPath(figureImgPath);
    // figure.setCite(cite);
    // figure.setFigcaption(figCaption);
    // figure.setHttpLink(httpLink);
    // }
    // return figure;
    // }

    public Connection getConnection() {
        Connection conn = null; //There is a connection cache in the DBUtil,only one connection instance keep in memory
        try {
            conn = DriverManager.getConnection(this.url, this.user, this.pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


	public ResultSet executeQuery(Connection conn, String staticSql) {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(staticSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int executeUpdate(Connection conn, String staticSql) {
        int returnCode=-1;
        try {
            Statement stmt = conn.createStatement();
            returnCode = stmt.executeUpdate(staticSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnCode;
    }
    
    public int executeUpdateWithRowID(Connection conn, String staticSql) {
        int infectRowId=1;
        try {
        	String getLastInsertIdSql="select last_insert_id()";
            Statement stmt = conn.createStatement();
             stmt.executeUpdate(staticSql);
             ResultSet rs = stmt.executeQuery(getLastInsertIdSql);
             while(rs.next()){
            	 infectRowId= rs.getInt(1);
            	 break;
             }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return infectRowId;
    }

    public void executeSQL(Connection conn, String staticSql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(staticSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeBatchSQL(Connection conn, List<String> sqlList) {
        try {
            Statement stmt = conn.createStatement();
            for (String sql : sqlList) {
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void closeConnection(Connection conn){
        try {
            if(!conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
		    String sql = "select * from timelinenode;";
		    DBUtil util =DBUtil.getInstance();
		    Connection conn=util.getConnection();
		    ResultSet rs=  util.executeQuery(conn, sql);
		    try {
                while(rs.next()){
                    System.out.println("******************");
                    System.out.println("ID: "+rs.getInt(1));
                    System.out.println("StartTime: "+rs.getDate(2));
                    System.out.println("EndTime: "+rs.getDate(3));
                    System.out.println("Header: "+rs.getString(4));
                    System.out.println("Article: "+rs.getString(5));
                    System.out.println("Figure ID: "+rs.getInt(6));
                    System.out.println("******************");
                    System.out.println();
                    System.out.println();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                try {
                    util.closeConnection(conn);
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
	}
}
