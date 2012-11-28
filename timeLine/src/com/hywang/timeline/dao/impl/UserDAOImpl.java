package com.hywang.timeline.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.hywang.timeline.dao.AbstractDatabaseDAO;
import com.hywang.timeline.dao.UserDAO;
import com.hywang.timeline.entity.User;

public class UserDAOImpl extends AbstractDatabaseDAO implements UserDAO {

    private static String getUserSQL = "select * from user where uid= ";
    
    private static String getUserByNameSQL = "select * from user where username= ";
    
    private static String deleteByIDSQL="delete from `timeline`.`user` WHERE `uid`= ";
    
    private static String deleteByNameSQL="delete from `timeline`.`user` WHERE `username`= ";

    public void deleteUserByID(int id) throws Exception {
        String sql=deleteByIDSQL+id;
        Connection conn = util.getConnection();
        try {
           util.executeUpdate(conn, sql);
       } catch (Exception e) {
         throw e;
       }finally{
           util.closeConnection(conn);
       }
    }
    
    public void deleteUserByName(String userName) throws Exception {
        String sql=deleteByNameSQL+"'"+userName+"'";
        Connection conn = util.getConnection();
        try {
           util.executeUpdate(conn, sql);
       } catch (Exception e) {
           util.closeConnection(conn);
         throw e;
       }finally{
           util.closeConnection(conn);
       }
    }

    public List<User> getAllUsers() {
        return null;
    }

    public User getUserByID(int id) {
        User user = null;
        String sql = getUserSQL + id;
        Connection conn = util.getConnection();
        ResultSet rs = util.executeQuery(conn, sql);
        try {
            while (rs.next()) {
                int uid = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String email = rs.getString(4);
                String uname = rs.getString(5);
                String pwd = rs.getString(6);
                user = new User();
                user.setId(uid);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setUserName(uname);
                user.setUserPwd(pwd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.closeConnection(conn);
        }
        return user;
    }
    
    public User getUserByName(String userName) {
        User user = null;
        String sql = getUserByNameSQL + "'"+userName+"'";
        Connection conn = util.getConnection();
        ResultSet rs = util.executeQuery(conn, sql);
        try {
            while (rs.next()) {
                int uid = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String email = rs.getString(4);
                String uname = rs.getString(5);
                String pwd = rs.getString(6);
                user = new User();
                user.setId(uid);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setUserName(uname);
                user.setUserPwd(pwd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.closeConnection(conn);
        }
        return user;
    }

    public void addUser(String userName, String pwd, String email, String firstName, String lastName) throws Exception {
        String insertSql = "INSERT INTO `timeline`.`user` (`firstname`, `lastname`, `email`, `username`, `password`) "
                + "VALUES " + "('" + firstName + "', '" + lastName + "', '" + email + "', '" + userName + "', '" + pwd + "');";
        Connection conn = util.getConnection();
         try {
            util.executeUpdate(conn, insertSql);
        } catch (Exception e) {
            util.closeConnection(conn);
            throw e;
          }finally{
              util.closeConnection(conn);
          }
    }
    
    public void addUser(User user) throws Exception {
        String insertSql = "INSERT INTO `timeline`.`user` (`firstname`, `lastname`, `email`, `username`, `password`) "
            + "VALUES " + "('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getEmail() + "', '" + user.getUserName() + "', '" + user.getUserPwd() + "');";
        Connection conn = util.getConnection();
     try {
        util.executeUpdate(conn, insertSql);
    } catch (Exception e) {
    	throw e;
      }finally{
          util.closeConnection(conn);
      }
        
    }
    
    public boolean getAutoLoginState(String uname, String sessionid) {
        boolean autologon=false;
        String sql = "select * from `timeline`.`logonvalidation`";
        String whereOptions= "where uname='"+uname+"' and sessionid='"+sessionid+"'";
        sql =sql+whereOptions;
        Connection conn = util.getConnection();
        ResultSet rs = util.executeQuery(conn, sql);
        try {
            while (rs.next()) {
              String username=  rs.getString(2);
              String sessionId= rs.getString(3);
              if(uname.equals(username) && sessionid.equals(sessionId)){
                  autologon =true;
              }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.closeConnection(conn);
        }
        return autologon;
    }

    public void insertUserLogonValidateInfo(String uname, String sessionId) throws Exception {
        String values="'"+ uname+"', '"+sessionId+"'";
        String sql = "insert into `timeline`.`logonvalidation` (`uname`, `sessionid`) VALUES ("+values+")";
        Connection conn = util.getConnection();
        try {
           util.executeUpdate(conn, sql);
       }catch (Exception e) {
               throw e;
        }finally{
             util.closeConnection(conn);
         }

    }
    public void deleteAutoLoginState(String uname,String sessionid)  throws Exception {
        String whereOptions="where uname='"+uname+"' and sessionid ='"+sessionid+"'";
        String sql = "delete from `timeline`.`logonvalidation` "+whereOptions; 
        Connection conn = util.getConnection();
        try {
            util.executeUpdate(conn, sql);
        }catch (Exception e) {
                throw e;
          }finally{
              util.closeConnection(conn);
          }
    }
    public User getAuthor(int nodeId) throws Exception {
		User author=null;
		int userId = 0;
		ResultSet rsUserId = null;
		String findUserIdSql = "select `timeline`.`noderelation`.`userid` from `timeline`.`noderelation` where `timeline`.`noderelation`.`nodeid`="+nodeId;
		Connection conn = util.getConnection();
		try {
			rsUserId = util.executeQuery(conn, findUserIdSql);
			while (rsUserId.next()) {
				userId=rsUserId.getInt(1);
				break;
			}
			author = getUserByID(userId);
		} catch (Exception e) {
			throw e;
		} finally {
			util.closeConnection(conn);
			rsUserId.close();
		}
		return author;
	}

    public static void main(String[] args) {
        UserDAOImpl userDAOImpl = new UserDAOImpl();
        try {
            userDAOImpl.addUser("Why", "why", "80981307@qq.com", "", "");
            System.out.println("User why has been created!!" );
            User user = userDAOImpl.getUserByName("Why");
            System.out.println("uname: "+user.getUserName());
            System.out.println("pwd: "+user.getUserPwd());
            System.out.println("email: "+user.getEmail());
            
//            userDAOImpl.deleteUserByID(2);
            userDAOImpl.insertUserLogonValidateInfo("kidbone1985", "aaa");
            System.out.println("User kidbone1985 remember the login status");
            System.out.println("kidbone1985's logon status:"+userDAOImpl.getAutoLoginState("kidbone1985", "aaa"));
            userDAOImpl.deleteAutoLoginState("kidbone1985", "aaa");
            System.out.println("kidbone1985's logon status has been deleted");
            userDAOImpl.deleteUserByName("Why");
            System.out.println("The user Why has been deleted!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


  

   

    

   
}
