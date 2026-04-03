<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 4/3/2026
  Time: 11:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.example.service.InternshipService" %>
<%@ page import="com.example.util.AuthUtil" %>
<%@ page import="com.example.dto.LoginResponse" %>

<%
    LoginResponse user = AuthUtil.requireUser(request, response);
    if (user == null) return;

    String idParam = request.getParameter("id");

    if (idParam != null) {
        try {
            Long id = Long.parseLong(idParam);

            InternshipService internshipService = new InternshipService();
            internshipService.delete(id, request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    response.sendRedirect("internships.jsp");
%>

<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
