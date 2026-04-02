<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/29/2026
  Time: 9:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.dto.LoginResponse" %>

<%
    LoginResponse user = (LoginResponse) session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Internship System</title>
</head>
<body>
    <div style="display:flex; justify-content:space-between; border-bottom:1px solid black; padding:10px;">
        <div>
            <b>Internship System</b>
        </div>

        <div>
            <%= user != null ? user.getEmail() : "" %>

            <form method="post" action="<%= request.getContextPath() %>/logout.jsp" style="display:inline;">
                <button type="submit">Logout</button>
            </form>
        </div>
    </div>
</body>