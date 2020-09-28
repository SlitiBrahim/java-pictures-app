<%@ page import="com.supinfo.suppictures.entity.Post" %><%--
  Created by IntelliJ IDEA.
  User: joristein
  Date: 2019-02-05
  Time: 23:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">

</head>
<body>

<% Post post = (Post) request.getAttribute("picture"); %>

<div class="container">
    <div class="gallery">
        <div class="gallery-item">
            <div class="title">
                <label> <%= post.getTitle()%> </label>
            </div>
            <img style="height: 100px" src="<%=post.getPath()%>" class="gallery-image" alt="'+ items[i].description + '">
            <div class="gallery-item-info"> </div>
            <div class="description">
                <label> <%=post.getDescription()%> </label>
            </div>
        </div>
    </div>
</div>
</body>
</html>
