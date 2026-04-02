<%@ page import="com.example.util.AuthUtil" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    LoginResponse user = AuthUtil.requireUser(request, response);
    if (user == null) return;
%>

<html>
<head>
    <title>Profile</title>
</head>
<body>
<jsp:include page="WEB-INF/layout/header.jsp"/>
Profile
<jsp:include page="WEB-INF/layout/footer.jsp"/>
</body>
</html>
