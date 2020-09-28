<style>
    #menu{
        height: 100%;
        width: 0;
        position: fixed;
        z-index: 1;
        top: 0;
        left: 0;
        background-color: grey;
        overflow: hidden;
        transition: 0.7s;
    }

    #menuOpen{
        position: fixed;
        top:0;
        left:0;
        width: 100%;
        font-size: 20px;
    }
    #menuOpen img {
        right:0;
        position: absolute;
    }
    #menuOpen span {
        margin-top: 15px;
        margin-left: 15px;
        position: absolute;
        left:0;
    }
    #menuClose {
        position: relative;
        top: 5px;
        left: 210px;
        font-size: 30px;
        color: #000000;
        text-decoration: none;
    }

    .buttonMenu{
        height: 30px;
        width: 250px;
        background-color: #e953d2;
        display: block;
        transition: 0.3s;
        padding: 5px;
        margin-top: 4px;
    }
    .buttonMenu:hover {
        background-color: #ffea50;
    }

</style>

<div id="menuOpen">
    <span style="cursor: pointer" onclick="openMenu()">&#9776; MENU</span>
    <img height="60px" width="60px" src="img/SupPictures_Logo.png" alt="logo"/>
</div>

<div id="menu">
    <a href="javascript:void(0)" id="menuClose" onclick="closeMenu()">&times;</a>
    <% if (session.getAttribute("id") == null) {%>
        <a href="${pageContext.request.contextPath}/login">LOGIN</a>
    <% } %>
    <br>
    <a href="${pageContext.request.contextPath}/register">REGISTER</a>
    <br>
    <% if (session.getAttribute("id") != null) {%>
        <a href="${pageContext.request.contextPath}/post">CREATE POST</a>
    <% } %>

</div>

<script>
    function openMenu() {
        document.getElementById("menu").style.width = "250px";
    }

    function closeMenu() {
        document.getElementById("menu").style.width = "0";
    }
</script>