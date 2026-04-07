<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.service.AuthService" %>
<%@ page import="com.example.dto.LoginResponse" %>

<%
    String message = null;

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty()) {
            message = "Email is required";

        } else if (password == null || password.trim().isEmpty()) {
            message = "Password is required";

        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            message = "Invalid email format";

        } else {
            try {
                AuthService authService = new AuthService();
                LoginResponse data = authService.login(email.trim(), password, request);

                session.setAttribute("user", data);
                response.sendRedirect(request.getContextPath() + "/internships.jsp");
                return;

            } catch (Exception e) {
                message = "Incorrect email or password";
            }
        }
    }
%>

<html>
<head>
    <title>Company Login</title>

    <script>
        function validateLoginForm() {
            const email = document.querySelector('[name="email"]').value;
            const password = document.querySelector('[name="password"]').value;

            if (!email.trim()) {
                alert("Email is required");
                return false;
            }

            if (!password.trim()) {
                alert("Password is required");
                return false;
            }

            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                alert("Invalid email format");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>

<h2>Company Login</h2>

<% if (message != null) { %>
<p style="color:red;"><%= message %></p>
<% } %>

<form method="post" action="login.jsp" onsubmit="return validateLoginForm()">
    <label>
        Email:
        <input type="email" name="email" required/>
    </label>
    <br/><br/>

    <label>
        Password:
        <input type="password" name="password" required/>
    </label>
    <br/><br/>

    <button type="submit">Login</button>
</form>

</body>
</html>