package com.hywang.timeline.entity;

import java.io.Serializable;
import java.util.Set;


public class User implements Serializable{
    
	private static final long serialVersionUID = 4173276172275267409L;

	private int id;
    
    private String firstName;
    
    private String lastName;
    
    private String userName;
    
    private String userPwd;
    
    private String email;
    
    private Set<TimeLineNode> nodes;


	public Set<TimeLineNode> getNodes() {
		return nodes;
	}


	public void setNodes(Set<TimeLineNode> nodes) {
		this.nodes = nodes;
	}


	public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }

    
    public String getFirstName() {
        return firstName;
    }

    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    public String getLastName() {
        return lastName;
    }

    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    public String getUserName() {
        return userName;
    }

    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    public String getUserPwd() {
        return userPwd;
    }

    
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }


    
    public String getEmail() {
        return email;
    }


    
    public void setEmail(String email) {
        this.email = email;
    }
}
