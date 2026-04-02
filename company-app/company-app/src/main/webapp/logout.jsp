<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        request.getSession().invalidate();
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
%>
<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
