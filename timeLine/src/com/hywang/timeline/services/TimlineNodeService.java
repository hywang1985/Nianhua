package com.hywang.timeline.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.persistence.dao.Initializable;

public interface TimlineNodeService extends Initializable{
    
    public TimeLineNode getNodeByID(int id,boolean needInitlized) throws DataAccessException;
	
    public List<TimeLineNode> getAllNodes()  throws DataAccessException; 
    
    public void deleteNode(TimeLineNode node) throws DataAccessException;
    
    public void deleteNodeById(int nodeId) throws DataAccessException;
    
    public int addNode(TimeLineNode node) throws DataAccessException;
    
    public void updateNode(TimeLineNode node);
}
