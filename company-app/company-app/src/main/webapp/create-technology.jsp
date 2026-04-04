<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 4/3/2026
  Time: 11:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.service.TechnologyService" %>

<%
    String name = request.getParameter("name");

    if (name != null && !name.isEmpty()) {
        TechnologyService service = new TechnologyService();
        try {
            service.create(name, request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    response.sendRedirect("create-update-internship.jsp");
%>
<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
