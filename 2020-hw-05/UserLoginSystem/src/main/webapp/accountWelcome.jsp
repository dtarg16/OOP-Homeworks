<%--
  Created by IntelliJ IDEA.
  User: David Targamadze
  Date: 7/5/2020
  Time: 9:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome <%= request.getParameter("username")%> </title>
</head>
<body>
<h1>Welcome <%= request.getParameter("username")%></h1>
</body>
</html>