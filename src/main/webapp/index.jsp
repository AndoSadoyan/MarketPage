<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/8/2025
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>

<form method="post" action="/register">
  <h2>Register to our page</h2>

  Email <input type="text" name="email" id="email" placeholder="Enter your email"><br><br>

  Username <input type="text" name="username" placeholder="Enter your username"><br><br>

  Password <input type="password" name="password" id="password" placeholder="Enter your password"><br><br>

  <%=request.getAttribute("ErrorMessage")==null?"":request.getAttribute("ErrorMessage")%> <br>
  <button type="submit">Register   </button>


  <!-- Link to sign in -->
  <a href="signin.jsp">Already have an account?</a>
</form>

</body>
</html>
