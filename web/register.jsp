<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="row justify-content-center mx-0 my-4">
        <h1 class="text-center">
            Register
        </h1>
    </div>
    <div class="row justify-content-center m-0">
        <div class="col-md-8 col-sm-12 justify-content-center my-3">
            <% if (request.getAttribute("error") != null){%>
                <div class="alert alert-danger col-md-5 col-sm-12 my-3 mx-auto text-center" role="alert">
                    <%=request.getAttribute("error")%>
                </div>
            <% }%>
            <form method="post" action="checkRegisterServlet">

                <div class="col-md-6 col-sm-12 mx-auto my-3">
                    <input type="text" id="username" class="form-group w-100 p-2" name="username" pattern="[a-zA-Z0-9]*" placeholder="username">
                </div>

                <div class="col-md-6 col-sm-12 mx-auto my-3">
                    <input type="password" id="password" class="form-group w-100 p-2" name="password" pattern="[a-zA-Z0-9:;./,_èéà()=+!ù%&]*" placeholder="password">
                </div>

                <div class="col-md-6 col-sm-12 mx-auto my-3">
                    <input type="password" id="confirm" class="form-group w-100 p-2" name="confirm" pattern="[a-zA-Z0-9:;./,_èéà()=+!ù%&]*" placeholder="confirm password">
                </div>

                <div class="col-md-6 col-sm-12 mx-auto my-3">
                    <input type="email" id="email" class="form-group w-100 p-2" name="email" placeholder="email">
                </div>

                <div class="col-md-6 col-sm-12 mx-auto my-3">
                    <input type="text" id="firstname" class="form-group w-100 p-2" name="firstname" pattern="[a-zA-Z]*" placeholder="firstname">
                </div>

                <div class="col-md-6 col-sm-12 mx-auto my-3">
                    <input type="text" id="lastname" class="form-group w-100 p-2" name="lastname" pattern="[a-zA-Z]*" placeholder="lastname">
                </div>

                <div class="col-md-6 col-sm-12 mx-auto my-3">
                    <input type="text" id="address" class="form-group w-100 p-2" name="address" pattern="[a-zA-Z0-9\s,']*" placeholder="postal address">
                </div>

                <div class="col-md-6 col-sm-12 mx-auto my-3">
                    <input type="tel" id="phone" class="form-group w-100 p-2" name="address" pattern="[0-9\s']*" placeholder="phone number">
                </div>

                <div class="col-md-2 col-sm-5 mx-auto my-3">
                    <input type="submit" id="submit" name="submit" class="rounded-pill btn btn-outline-primary w-100 py-2" value="SUBMIT">
                </div>
            </form>
        </div>
    </div>
</body>
</html>