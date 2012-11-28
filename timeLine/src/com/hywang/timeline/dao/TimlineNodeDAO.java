package com.hywang.timeline.dao;

import java.util.List;

import com.hywang.timeline.entity.TimeLineNode;

public interface TimlineNodeDAO {
    
    public TimeLineNode getNodeByID(int id) throws Exception;
    
    public List<TimeLineNode> getNodesByUserID(int userid) throws Exception;
    
    public List<TimeLineNode> getAllNodes()  throws Exception; 
    
    public boolean delNodeByID(int id) throws Exception;
    
    public void deleteNodeRelation(int userid,int nodeid) throws Exception;
    
    public List<TimeLineNode> getNodesByCategory(int cateID);
    
    public int addNode(TimeLineNode node) throws Exception;
    
    public boolean updateNodeRelation(int userid,int nodeid) throws Exception;
}
