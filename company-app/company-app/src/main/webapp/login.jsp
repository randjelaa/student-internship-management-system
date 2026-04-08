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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Company Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">

    <script>
        function validateLoginForm() {
            const email = document.querySelector('[name="email"]').value;
            const password = document.querySelector('[name="password"]').value;
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            if (!email.trim() || !password.trim()) {
                alert("Please fill in both fields");
                return false;
            }
            if (!emailRegex.test(email)) {
                alert("Invalid email format");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

<div class="login-card text-center">
    <h1 class="h3 mb-3 fw-bold text-primary">Internship System</h1>
    <p class="text-muted mb-4">Please sign in to continue</p>

    <% if (message != null) { %>
    <div class="alert alert-danger py-2 small"><%= message %></div>
    <% } %>

    <form method="post" action="login.jsp" onsubmit="return validateLoginForm()" class="text-start">
        <div class="mb-3">
            <label class="form-label small fw-bold text-uppercase">Email address</label>
            <input type="email" name="email" class="form-control form-control-lg"
                   placeholder="name@company.com" required/>
        </div>

        <div class="mb-4">
            <label class="form-label small fw-bold text-uppercase">Password</label>
            <input type="password" name="password" class="form-control form-control-lg"
                   placeholder="••••••••" required/>
        </div>

        <div class="d-grid">
            <button type="submit" class="btn btn-primary btn-lg">Login</button>
        </div>
    </form>
</div>

</body>
</html>