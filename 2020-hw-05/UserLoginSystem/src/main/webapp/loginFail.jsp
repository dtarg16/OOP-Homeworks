<%--
  Created by IntelliJ IDEA.
  User: David Targamadze
  Date: 7/5/2020
  Time: 9:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Information Incorrect</title>
</head>
<body>
<h1>Please Try Again</h1>

<p>Either your user name or password is incorrect. Please try again.</p>

<form action="login" method="post">
    User Name: <input type="text" name="username"/> <br/>
    Password: <input type="text" name="password"/>
    <<input type="submit" value="Login"/>
</form>

<a href="createAccount.jsp">Create New Account</a>
</body>
</html>