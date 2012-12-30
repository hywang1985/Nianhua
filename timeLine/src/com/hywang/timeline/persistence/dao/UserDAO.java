package com.hywang.timeline.persistence.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.hywang.timeline.entity.LogonValidationInfo;
import com.hywang.timeline.entity.User;


public interface UserDAO {
    
    public void addUser(String userName,String pwd,String email,String firstName,String lastName)  throws DataAccessException;

    public void addUser(User user) throws DataAccessException;
    
    public User getUserByID(int id);
    
    public User getUserByName(String userName);
    
    public List<User> getAllUsers();
    
    public void deleteUserByID (int userId) throws DataAccessException;
    
    public void insertUserLogonValidateInfo(LogonValidationInfo logonInfo)  throws DataAccessException;
    
    public boolean getAutoLoginState(String uname, String sessionId)  throws DataAccessException ; 
    
    public void deleteAutoLoginState(String uname,String sessionId)  throws DataAccessException ;
}
