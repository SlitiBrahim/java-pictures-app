package com.supinfo.suppictures.dao;

import com.supinfo.suppictures.entity.Member;
import com.supinfo.suppictures.entity.Post;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO extends DAO<Member> {
    private Statement state;
    private Connection connection;


    public MemberDAO() {
        connection = ConnexionDB.Connect();
        try {
            assert connection != null;
            state = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Member create(Member member) {
        try {
            state.executeUpdate(
                    "INSERT INTO Members (username, password, firstname, lastname, phoneNumber, adresse, email) " +
                            "VALUES('"
                            + member.getUsername()
                            +"', '" + passwordToHash(member.getPassword())
                            +"', '" + member.getFirstname()
                            +"', '" + member.getLastname()
                            +"', '" + member.getPhoneNumber()
                            +"', '" + member.getAddress()
                            +"', '" + member.getEmail() + "')");
            Long memberInsertedId = getLastIdInsertedMember();
            return find(memberInsertedId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean delete(Member member) {
        try {
            state.executeUpdate("DELETE FROM Members WHERE id = " + member.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void delete(Long id) {
        try {
            state.execute("DELETE FROM Members WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Member update(Member member) {
        try {
            state.executeUpdate("UPDATE Members " +
                    "SET username = '" + member.getUsername()
                    + "', password ='" + passwordToHash(member.getPassword())
                    + "', firstname = '" + member.getFirstname()
                    + "', lastname ='" + member.getLastname()
                    + "', phoneNumber = '" + member.getPhoneNumber()
                    + "', adresse ='" + member.getAddress()
                    + "', email = '" + member.getEmail()
                    + "', isAdmin = " + member.getIsAdmin()
                    + " WHERE id =" + member.getId());

            return find(member.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Member find(Long id) {
        Member member = null;
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM Members WHERE id = " + id);

            if(result.first()) {
                member = new Member(id
                        , result.getString("username")
                        , result.getString("password")
                        , result.getString("firstname")
                        , result.getString("lastname")
                        , result.getString("phoneNumber")
                        , result.getString("adresse")
                        , result.getString("email")
                        , result.getBoolean("isAdmin"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return member;
    }

    public List<Member> findAllMember() {
        List<Member> memberlist = new ArrayList<Member>();
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM Members");

            if(result.first()){
                do{
                    memberlist.add(
                        new Member(result.getLong("id")
                                , result.getString("username")
                                , result.getString("password")
                                , result.getString("firstname")
                                , result.getString("lastname")
                                , result.getString("phoneNumber")
                                , result.getString("adresse")
                                , result.getString("email")
                                , result.getBoolean("isAdmin"))
                    );
                }while(result.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return memberlist;
    }

    public int findNumberOfMembers() {
        int numberOfMember = 0;
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT COUNT(*) FROM Members");

            if(result.first()) {
                numberOfMember = result.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfMember;
    }

    public Member ComparePassword(String inputUsername, String inputPassword){
        Member member = null;
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM Members WHERE username = '" + inputUsername
                    + "' AND password = '" + passwordToHash(inputPassword) + "'");

            if(result.first()) {
                member = new Member(result.getLong("id")
                        , result.getString("username")
                        , result.getString("password")
                        , result.getString("firstname")
                        , result.getString("lastname")
                        , result.getString("phoneNumber")
                        , result.getString("adresse")
                        , result.getString("email")
                        , result.getBoolean("isAdmin"));
            }
            return member;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    private String passwordToHash(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] messageDigest = md.digest(password.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            StringBuilder hashPassword = new StringBuilder(no.toString(16));

            while (hashPassword.length() < 32) {
                hashPassword.insert(0, "0");
            }

            return hashPassword.toString();
        }

        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Member> findAllMembers() {
        List<Member> membersList = new ArrayList<>();
        try {
            ResultSet result = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM Members");

            addResultSetToMemberList(membersList, result);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return membersList;
    }


    private void addResultSetToMemberList(List<Member> membersList, ResultSet result) throws SQLException {
        if(result.first()){
            do{
                membersList.add(
                        new Member(
                                result.getLong("id")
                                , result.getString("username")
                                , result.getString("password")
                                , result.getString("firstname")
                                , result.getString("lastname")
                                , result.getString("phoneNumber")
                                , result.getString("address")
                                , result.getString("email")
                                , result.getBoolean("isAdmin"))
                );
            }while(result.next());
        }
    }

    static Long getTheLastIdInserted(String sql, Connection connection){
        long id = 0L;
        try {
            ResultSet result = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY
                ).executeQuery(sql);

            if(result.first()) {
                id = result.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return id;
    }

    private Long getLastIdInsertedMember(){
        String sql = "SELECT id FROM Members ORDER BY id DESC LIMIT 1";
        return getTheLastIdInserted(sql, this.connection);
    }
}
