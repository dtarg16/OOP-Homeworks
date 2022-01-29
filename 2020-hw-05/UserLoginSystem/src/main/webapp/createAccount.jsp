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
    <title>Create Account</title>
</head>
<body>
<h1>Create New Account</h1>

<p>Please enter proposed name and Password</p>

<form action="create" method="post">
    User Name: <input type="text" name="username"/> <br/>
    Password: <input type="text" name="password"/>
    <input type="submit" value="Login"/>
</form>
</body>
</html>
