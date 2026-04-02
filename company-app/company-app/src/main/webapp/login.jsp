<%@ page import="com.example.service.AuthService" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String error = null;

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            AuthService authService = new AuthService();
            LoginResponse data = authService.login(email, password, request);
            session.setAttribute("user", data);
            response.sendRedirect(request.getContextPath() + "/internships.jsp");
            return;

        } catch (Exception e) {
            error = "Incorrect email or password";
        }
    }
%>

<html>
<head>
    <title>Company Login</title>
</head>
<body>

<h2>Company Login</h2>

<form method="post" action="login.jsp">
    <label>
        Email:
        <input type="text" name="email" required/>
    </label>
    <br/><br/>

    <label>
        Password:
        <input type="password" name="password" required/>
    </label>
    <br/><br/>

    <button type="submit">Login</button>
</form>

<% if (error != null) { %>
<p style="color:red;"><%= error %></p>
<% } %>

</body>
</html>