<%@ page import="com.supinfo.suppictures.entity.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SupPictures</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
</head>
    <body>
    <%@ include file="header.jsp" %>
        <!-- HEADER -->
        <div class="row justify-content-center mx-0 my-4">
            <h1 class="text-center">
                POST
            </h1>
        </div>
        <div class="row justify-content-center m-0">
            <div class="col-md-8 col-sm-12 justify-content-center my-3">
                <% if (request.getAttribute("error") != null){%>
                    <div class="alert alert-danger col-md-5 col-sm-12 my-3 mx-auto text-center" role="alert">
                        <%=request.getAttribute("error")%>
                    </div>
                <% }%>
                <% if (request.getAttribute("success") != null){%>
                <div class="alert alert-success col-md-5 col-sm-12 my-3 mx-auto text-center" role="alert">
                    <%=request.getAttribute("success")%>
                </div>
                <% }%>
                <form method="post" action="uploadServlet" id="postForm" enctype="multipart/form-data">

                    <div class="col-md-6 col-sm-12 mx-auto my-3">
                        <input type="text" id="title" name="title" class="form-group w-100 p-2" placeholder="Photo's title" minlength="3" maxlength="50" pattern="[/s a-ZA-Z,:!-()]*" value="" required>
                    </div>

                    <div class="col-md-6 col-sm-12 mx-auto my-3">
                        <textarea id="description" name="description" placeholder="Description" form="postForm" maxlength="300" class="form-group w-100" style="height: 130px;"></textarea>
                    </div>
                    <div class="form-row col-md-6 mx-auto">
                        <div class="form-group col-md-6">
                            <label for="category">Category</label>
                            <select name="category" id="category" class="form-control pl-2" form="postForm" required>
                                <% for (Category category:(List<Category>) request.getAttribute("categoryList")) { %>
                                <option value="<%= category.getId() %>"><%= category.getCategoryName() %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="locality">Locality</label>
                            <input name="locality" id="locality" class="form-control pr-2" placeholder="Locality" minlength="2" maxlength="20" pattern="[a-zA-Z-]*" required>
                        </div>
                    </div>

                    <div class="col-md-6 col-sm-12 mx-auto my-3">
                        <input type="file" id="picture" name="picture" class="form-group" required>
                    </div>

                    <div class="col-md-2 col-sm-5 mx-auto my-3">
                        <input type="submit" id="submit" name="submit" class="rounded-pill btn btn-outline-primary w-100 py-2" value="SUBMIT">
                    </div>

                </form>
            </div>
        </div>
        <!-- FOOTER -->
    </body>
</html>