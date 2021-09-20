<%--
  Created by IntelliJ IDEA.
  User: truon
  Date: 16/09/2021
  Time: 00:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<body>
<h1>admin</h1>
<form action="/admin" method="post">
    <input type="text" placeholder="name" name="userName">
    <input type="password" placeholder="password" name="password">
    <input type="submit" value="login">
</form>
</body>
</html>
