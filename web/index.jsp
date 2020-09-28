<%@ page import="com.supinfo.suppictures.dao.MemberDAO" %>
<%@ page import="com.supinfo.suppictures.dao.PostDAO" %>
<%@ page import="com.supinfo.suppictures.dao.CategoryDAO" %>
<%@ page import="com.supinfo.suppictures.entity.Category" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: brahim
  Date: 2019-02-01
  Time: 07:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script> <%@include file="js/dropdown.js"%></script>
    <script> <%@include file="js/pictures.js"%></script>
    <script> <%@include file="js/request.js"%></script>
  </head>
  
  <body>
   <%@ include file="header.jsp" %>

   <style><%@include file="css/index.css" %></style>
   <style><%@include file="css/searchbar.css" %></style>


   <%-- STATISTIQUE --%>
   <div class="container banner">
     <table class="info_table">
       <tr>
         <td><span> Number of users: </span></td>
         <td>   <% MemberDAO memberDAO = new MemberDAO();
                int nbofusers = memberDAO.findNumberOfMembers();%>
                <%= nbofusers %></td>
       </tr>
       <tr>
         <td>
           <span> Number of pictures: </span>
         </td>
           <td> <% PostDAO postDAO = new PostDAO();
                int nbofposts = postDAO.findNumberOfPost();
                %><%= nbofposts %></td>
       </tr>
     </table>
   </div>


    <%-- BAR DE RECHERCHE --%>
   <div class="wrap">
       <div class="search">
           <input type="text" id="keywords" class="searchTerm" placeholder="Keywords">
           <select id="category" class="searchMenu">
               <%
                   CategoryDAO categoryDAO = new CategoryDAO();
                   List<Category> categories = categoryDAO.findAllCategory();
                   for (Category category: categories){ %>
                    <option value="<%= category.getId() %>"><%= category.getCategoryName() %></option>
               <%}%>
           </select>
           <button type="submit" class="searchButton" onclick="searchEngine()"></button>
       </div>
   </div>



   <div class="container">
       <div class="gallery " id="pictures"></div>
   </div>

   <%@ include file="footer.jsp" %>

  </body>
</html>
