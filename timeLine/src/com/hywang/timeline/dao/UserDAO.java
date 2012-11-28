package com.hywang.timeline.dao;

import java.util.List;

import com.hywang.timeline.entity.User;


public interface UserDAO {
    
    public void addUser(String userName,String pwd,String email,String firstName,String lastName)  throws Exception;

    public void addUser(User user) throws Exception;
    
    public User getUserByID(int id);
    
    public User getUserByName(String userName);
    
    public List<User> getAllUsers();
    
    public void deleteUserByID (int id) throws Exception;
    
    public void deleteUserByName(String userName) throws Exception;
    
    public void insertUserLogonValidateInfo(String uname,String sessionId)  throws Exception;
    
    public boolean getAutoLoginState(String uname, String sessionId)  throws Exception ; 
    
    public void deleteAutoLoginState(String uname,String sessionId)  throws Exception ;
    
    public User getAuthor(int nodeId)  throws Exception;
}
