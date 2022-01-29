<%--
  Created by IntelliJ IDEA.
  User: David Targamadze
  Date: 7/5/2020
  Time: 1:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Welcome to Homework 5</h1>

<p>Please log in</p>

<form action="login" method="post">
    User Name: <input type="text" name="username"/> <br/>
    Password: <input type="text" name="password"/>
<%--    <button type="submit"> Login</button>--%>
    <input type="submit" value="Login"/>
</form>

<a href="createAccount.jsp">Create New Account</a>
</body>
</html>
