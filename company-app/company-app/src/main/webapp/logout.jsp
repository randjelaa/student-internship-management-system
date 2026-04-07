<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        request.getSession().invalidate();
        response.sendRedirect("login.jsp");
        return;
    }
%>