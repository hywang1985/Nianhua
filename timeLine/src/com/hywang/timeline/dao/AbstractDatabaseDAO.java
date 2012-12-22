package com.hywang.timeline.dao;


import com.hywang.timeline.DAOFactory;
import com.hywang.timeline.utils.database.DBUtil;


public abstract class AbstractDatabaseDAO {
    
    protected static DBUtil util = DBUtil.getInstance();
    
    protected static DAOFactory daoFactory =DAOFactory.getInstance();

}