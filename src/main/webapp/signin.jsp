<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/8/2025
  Time: 3:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in</title>
</head>
<body>
<form method="post" action="/login">
  <h2>Sign in</h2>

  Email <input type="text" name="email" placeholder="Enter you email"><br><br>

  Password <input type="password" name="password" placeholder="Enter your password"><br><br>



  <%=request.getAttribute("ErrorMessage")==null?"":request.getAttribute("ErrorMessage")%><br>

  <button type="submit" style="border-collapse: collapse; padding-right: 2%">Sign in</button>
  remember me<input type="checkbox" name="remember">

</form>

</body>
</html>
