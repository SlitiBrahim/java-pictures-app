<%@ page import="com.supinfo.suppictures.dao.MemberDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.supinfo.suppictures.entity.Member" %>
<%@ page import="com.supinfo.suppictures.dao.PostDAO" %>
<%@ page import="com.supinfo.suppictures.entity.Post" %>
<%@ page import="com.supinfo.suppictures.dao.CategoryDAO" %>
<%@ page import="com.supinfo.suppictures.entity.Category" %><%--
  Created by IntelliJ IDEA.
  User: joristein
  Date: 2019-02-04
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <style><%@include file="css/index.css" %></style>
    <script> <%@include file="js/request.js"%></script>

</head>
<body>

<%@ include file="header.jsp" %>

<%  MemberDAO memberDAO= new MemberDAO();
    List<Member> members = memberDAO.findAllMember();

    PostDAO postDAO= new PostDAO();
    List<Post> posts = postDAO.findAllPosts();
%>

    <div class="container" style="margin-top: 100px; margin-bottom: 100px">
        <fieldset>
            <legend> MEMBERS MANAGER:</legend>
            <div class="container banner">
                <table class="info_table">
                    <% for (Member member: members){ %>
                        <tr>
                            <td>
                                <%= member.getUsername() %>
                            </td>
                            <td>
                                <%= member.getPhoneNumber()%>
                            </td>
                            <td>
                                <%= member.getFirstname()%>
                            </td>
                            <td>
                                <%= member.getLastname()%>
                            </td>
                            <td>
                                <%= member.getAddress()%>
                            </td>
                            <td>
                                <%= member.getEmail()%>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/delete/user?id=<%=member.getId()%>">
                                    <button> DELETE </button>
                                </a>
                            </td>
                        </tr>
                    <%}%>
                </table>
            </div>
        </fieldset>
    </div>

    <div class="container" style="margin-top: 100px; margin-bottom: 100px">
        <fieldset>
            <legend> CATEGORY MANAGER:</legend>
            <form action="${pageContext.request.contextPath}/admin/delete/category">

                <select id="category" class="searchMenu" name="id">
                <%
                    CategoryDAO categoryDAO = new CategoryDAO();
                    List<Category> categories = categoryDAO.findAllCategory();
                    for (Category category: categories){ %>
                <option value="<%= category.getId() %>"><%= category.getCategoryName() %></option>
                <%}%>
                </select>

                <input type="submit" value="DELETE">
            </form>


            <form action="${pageContext.request.contextPath}/admin/create/category">
                <input type="text" name="name"/>

                <input type="submit" value="CREATE"/>
            </form>
        </fieldset>
    </div>


    <div class="container">
        <div class="gallery">
            <%
            for (Post post: posts){ %>
                <div class="gallery-item">
                    <div class="title">
                        <label> <%= post.getTitle()%> </label>
                    </div>
                    <img style="height: 100px" src="<%=post.getPath()%>" class="gallery-image" alt="'+ items[i].description + '">
                    <div class="gallery-item-info"> </div>
                    <div class="description">
                        <label> <%=post.getDescription()%> </label>
                    </div>
                    <a href="${pageContext.request.contextPath}/admin/delete/post?id=<%=post.getId()%>"> <button style="color: white; width:100%; background-color: #a40000;">DELETE</button></a>
                </div>
            <%}%>
        </div>
    </div>
</div>
</body>
</html>
