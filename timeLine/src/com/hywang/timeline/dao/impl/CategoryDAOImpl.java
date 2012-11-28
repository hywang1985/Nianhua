package com.hywang.timeline.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hywang.timeline.DAOFactory;
import com.hywang.timeline.dao.AbstractDatabaseDAO;
import com.hywang.timeline.dao.CategoryDAO;
import com.hywang.timeline.entity.Category;


public class CategoryDAOImpl extends AbstractDatabaseDAO implements CategoryDAO {
    
    private static String allCategoriesSql="select * from `timeline`.`category`;";
    
    private static String catgoryIDSql="select * from `timeline`.`category` where cid = ";

    public List<Category> getAllCategories() {
        List<Category> allCategories=new ArrayList<Category>();
        Connection conn = util.getConnection();
        ResultSet rsCategory = util.executeQuery(conn, allCategoriesSql);
        try {
            while (rsCategory.next()) {
                int id=rsCategory.getInt(1);
                String name=rsCategory.getString(2);
                Category cate = new Category();
                cate.setId(id);
                cate.setName(name);
                allCategories.add(cate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            util.closeConnection(conn);
        }
        return allCategories;
    }

    public Category getCategoryByID(int ID) {
        Category cate =null;
        String cSql=catgoryIDSql+ ID + ";";
        Connection conn = util.getConnection();
        ResultSet rsCategory = util.executeQuery(conn, cSql);
        try {
            while (rsCategory.next()) {
                int id=rsCategory.getInt(1);
                String name=rsCategory.getString(2);
                cate = new Category();
                cate.setId(id);
                cate.setName(name);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            util.closeConnection(conn);
        }
        return cate;
    }
    
    
    public static void main(String[] args) {
        List<Category> allCategories = DAOFactory.getInstance().createCategoryDAO().getAllCategories();
        for(Category cate:allCategories){
            System.out.println("***********");
            System.out.println(cate.getId());
            System.out.println(cate.getName());
            System.out.println("***********");
            System.out.println();
        }
    }
}
