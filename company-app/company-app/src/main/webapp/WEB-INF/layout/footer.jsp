<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/29/2026
  Time: 9:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <hr/>
    <div style="display:flex; justify-content:space-around;">
        <a href="<%= request.getContextPath() %>/internships.jsp">Internships</a>
        <a href="<%= request.getContextPath() %>/applications.jsp">Applications</a>
        <a href="<%= request.getContextPath() %>/grades.jsp">Grades</a>
        <a href="<%= request.getContextPath() %>/profile.jsp">Profile</a>
    </div>
</body>
</html>

