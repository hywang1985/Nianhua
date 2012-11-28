package com.hywang.timeline;

import com.hywang.timeline.dao.CategoryDAO;
import com.hywang.timeline.dao.TimlineNodeDAO;
import com.hywang.timeline.dao.UserDAO;
import com.hywang.timeline.dao.impl.CategoryDAOImpl;
import com.hywang.timeline.dao.impl.TimelineNodeDAOImpl;
import com.hywang.timeline.dao.impl.UserDAOImpl;

/*factory used to get dao*/
public class DAOFactory {
    
    private static DAOFactory instance =null;
    
    private DAOFactory(){
        
    }
    
    public static synchronized DAOFactory getInstance(){
        if(instance==null){
            instance =new DAOFactory();
        }
        return instance;
    }
    
    public TimlineNodeDAO createTimelineNodeDAO(){
        return new TimelineNodeDAOImpl();
    }
    
    
    public CategoryDAO createCategoryDAO(){
        return new CategoryDAOImpl();
    }
    
    public UserDAO createUserDAO(){
        return new UserDAOImpl();
    }
}
