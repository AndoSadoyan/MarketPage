<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/8/2025
  Time: 7:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change</title>
</head>
<body>

<form method="post" action="/changePassword">
  New Password <input type="password" name="new1"><br><br>

  Repeat <input type="password" name="new2"><br><br>

  <%=request.getAttribute("ErrorMessage")==null?"":request.getAttribute("ErrorMessage")%>
  <br>

  <button type="submit">Change</button>
</form>
</body>
</html>
