package com.supinfo.suppictures.dao;

import com.supinfo.suppictures.entity.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.supinfo.suppictures.dao.MemberDAO.getTheLastIdInserted;

public class PostDAO extends DAO<Post> {
    private Statement state;
    private Connection connection;

    public PostDAO() {
        connection = ConnexionDB.Connect();
        try {
            assert connection != null;
            state = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post create(Post post) {
        java.util.Date now = new java.util.Date();
        java.sql.Timestamp nowSql = new java.sql.Timestamp(now.getTime());
        try {
            state.executeUpdate(
                    "INSERT INTO Post (title, description, path, createdAt, updatedAt, locality, categoryId, memberId) " +
                            "VALUES('"
                            + post.getTitle().replace("'","''")
                            +"', '" + post.getDescription().replace("'","''")
                            +"', '" + post.getPath()
                            +"', '" + nowSql
                            +"', '" + nowSql
                            +"', '" + post.getLocality().replace("'","''")
                            +"', '" + post.getCategoryId()
                            +"', '" + post.getMemberId()+ "')");
            Long postInsertedId = getLastIdInsertedPost();
            return find(postInsertedId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean delete(Post post) {
        try {
            state.executeUpdate("DELETE FROM Post WHERE id = " + post.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void delete(Long id) {
        try {
            state.executeUpdate("DELETE FROM Post WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post update(Post post) {
        java.util.Date now = new java.util.Date();
        java.sql.Date nowSql = new java.sql.Date(now.getTime());
        try {
            state.executeUpdate("UPDATE Post " +
                    "SET title = '" + post.getTitle().replace("'","''")
                    + "', description ='" + post.getDescription().replace("'","''")
                    + "', path = '" + post.getPath()
                    + "', updatedAt ='" + nowSql
                    + "', locality = '" + post.getLocality().replace("'","''")
                    + "', categoryId ='" + post.getCategoryId()
                    + "', memberId = '" + post.getMemberId()
                    + "' WHERE id =" + post.getId());

            return find(post.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Post find(Long id) {
        Post post = null;
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM Post WHERE id = " + id);

            if(result.first()) {
                post = new Post(id
                        , result.getString("title")
                        , result.getString("description")
                        , result.getString("path")
                        , result.getString("createdAt")
                        , result.getString("updatedAt")
                        , result.getString("locality")
                        , result.getLong("categoryId")
                        , result.getLong("memberId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return post;
    }

    public Boolean deleteAllPostOfMember(Long MemberId) {
        try {
            state.executeUpdate("DELETE FROM Post WHERE memberId = " + MemberId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int findNumberOfPost() {
        int numberOfPost = 0;
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT COUNT(*) FROM Post");

            if(result.first()) {
                numberOfPost = result.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfPost;
    }

    public List<Post> findAllPost(int limit) {
        List<Post> postList = new ArrayList<Post>();
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM Post LIMIT " + limit);

            addResultSetToPostList(postList, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return postList;
    }

    public List<Post> findAllPostOfMember(int memberId) {
        List<Post> postList = new ArrayList<Post>();
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM post WHERE memberId = " + memberId);

            addResultSetToPostList(postList, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return postList;
    }


    public List<Post> findAllPosts() {
        List<Post> postList = new ArrayList<Post>();
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM post");
            addResultSetToPostList(postList, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return postList;
    }

    private void addResultSetToPostList(List<Post> postList, ResultSet result) throws SQLException {
        if(result.first()){
            do{
                postList.add(
                        new Post(
                                result.getLong("id")
                                , result.getString("title")
                                , result.getString("description")
                                , result.getString("path")
                                , result.getString("createdAt")
                                , result.getString("updatedAt")
                                , result.getString("locality")
                                , result.getLong("categoryId")
                                , result.getLong("memberId"))
                );
            }while(result.next());
        }
    }

    public List<Post> findPostsBySearch(String searchWord, Long searchCategoryId) {
        List<Post> postList = new ArrayList<Post>();
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM Post" +
                    " WHERE (title LIKE '%" + searchWord + "%'" +
                    " OR description LIKE '%" + searchWord + "%'" +
                    " OR locality LIKE '%" + searchWord + "%')" +
                    (searchCategoryId != null ? " AND categoryId = " + searchCategoryId : "")
            );

            addResultSetToPostList(postList, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return postList;
    }

    private Long getLastIdInsertedPost(){
        String sql = "SELECT id FROM Post ORDER BY id DESC LIMIT 1";
        return getTheLastIdInserted(sql, this.connection);
    }
}
