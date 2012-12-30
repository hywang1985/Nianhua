package com.hywang.timeline.persistence.dao;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.entity.User;

public interface TimlineNodeDAO {
    
    public TimeLineNode getNodeByID(int id) throws DataAccessException;
	
    public  Set<TimeLineNode> getNodesByUser(User user) throws DataAccessException;
    
    public List<TimeLineNode> getAllNodes()  throws DataAccessException; 
    
    public boolean deleteNode(TimeLineNode node) throws DataAccessException;
    
    public boolean deleteNodeById(int nodeId) throws DataAccessException;
    
    public int addNode(TimeLineNode node) throws DataAccessException;
}
