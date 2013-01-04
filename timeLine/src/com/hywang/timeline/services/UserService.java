package com.hywang.timeline.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.hywang.timeline.entity.LogonValidationInfo;
import com.hywang.timeline.entity.User;
import com.hywang.timeline.persistence.dao.Initializable;


public interface UserService extends Initializable{
    
    public void addUser(String userName,String pwd,String email,String firstName,String lastName)  throws DataAccessException;

    public void addUser(User user) throws DataAccessException;
    
    public User getUserByID(int id,boolean initChildren);
    
    public User getUserByName(String userName ,boolean initChildren);
    
    public List<User> getAllUsers();
    
    public void deleteUserByID (int userId) throws DataAccessException;
    
    public void insertUserLogonValidateInfo(LogonValidationInfo logonInfo)  throws DataAccessException;
    
    public boolean getAutoLoginState(String uname, String sessionId)  throws DataAccessException ; 
    
    public void deleteAutoLoginState(String uname,String sessionId)  throws DataAccessException ;
}
