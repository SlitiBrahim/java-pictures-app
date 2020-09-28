package com.supinfo.suppictures.dao;


import com.supinfo.suppictures.entity.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.supinfo.suppictures.dao.MemberDAO.getTheLastIdInserted;

public class CategoryDAO extends DAO<Category> {
    private Statement state;
    private Connection connection;

    public CategoryDAO() {
        connection = ConnexionDB.Connect();
        try {
            assert connection != null;
            state = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Category create(Category category) {
        try {
            state.executeUpdate(
                    "INSERT INTO category (categoryName) VALUES('" + category.getCategoryName() + "')");
            Long categoryInsertedId = getLastIdInsertedCategory();
            return find(categoryInsertedId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean delete(Category category) {
        try {
            state.executeUpdate("DELETE FROM category WHERE id = " + category.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Category update(Category category) {
        try {
            state.executeUpdate("UPDATE category " +
                    "SET categoryName = '" + category.getCategoryName()
                    + "' WHERE id =" + category.getId());
            return find(category.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category find(Long id) {
        Category category = null;
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM category WHERE id = " + id);

            if(result.first()) {
                category = new Category(id, result.getString("categoryName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return category;
    }

    public List<Category> findAllCategory() {
        List<Category> categoryList = new ArrayList<Category>();
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM category");

            if(result.first()){
                do{
                    categoryList.add(new Category(result.getLong("id"), result.getString("categoryName")));
                }while(result.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return categoryList;
    }

    private Long getLastIdInsertedCategory(){
        String sql = "SELECT id FROM Category ORDER BY id DESC LIMIT 1";
        return getTheLastIdInserted(sql, this.connection);
    }

}
