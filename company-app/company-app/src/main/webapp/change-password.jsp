<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page import="com.example.service.PasswordService" %>

<%
    LoginResponse user = (LoginResponse) request.getSession().getAttribute("user");
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    String message = null;
    PasswordService passwordService = new PasswordService();

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String current = request.getParameter("currentPassword");
        String newPass = request.getParameter("newPassword");
        String confirm = request.getParameter("confirmPassword");

        if (newPass.length() < 6) {
            message = "Password must be at least 6 characters";
        } else if (!newPass.equals(confirm)) {
            message = "New passwords do not match";
        } else {
            try {
                passwordService.changePassword(current, newPass, request);
                message = "Password changed successfully";
            } catch (Exception e) {
                message = "Failed to change password";
                e.printStackTrace();
            }
        }
    }
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Change Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <script>
        function validateForm() {
            const current = document.querySelector('[name="currentPassword"]').value;
            const newPass = document.querySelector('[name="newPassword"]').value;
            const confirm = document.querySelector('[name="confirmPassword"]').value;

            if (!current || !newPass || !confirm) {
                alert("All fields are required");
                return false;
            }
            if (newPass.length < 6) {
                alert("Password must be at least 6 characters");
                return false;
            }
            if (newPass !== confirm) {
                alert("Passwords do not match");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/layout/header.jsp"/>
</header>

<main>
    <div class="container pb-5">
        <h2 class="h4 my-4 fw-bold text-center">Security Settings</h2>

        <div class="card border-0 shadow-sm">
            <div class="card-body">
                <p class="small text-muted mb-4">
                    Logged in as: <strong><%= user.getEmail() %></strong>
                </p>

                <% if (message != null) { %>
                <div class="alert alert-success py-2 small"><%= message %></div>
                <% } %>

                <form method="post" onsubmit="return validateForm()">
                    <div class="mb-3">
                        <label class="form-label small fw-bold">Current Password</label>
                        <input type="password" name="currentPassword" class="form-control" required/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label small fw-bold">New Password</label>
                        <input type="password" name="newPassword" class="form-control"
                               placeholder="Min. 6 characters" required/>
                    </div>

                    <div class="mb-3">
                        <label class="form-label small fw-bold">Confirm New Password</label>
                        <input type="password" name="confirmPassword" class="form-control" required/>
                    </div>

                    <div class="d-grid gap-2 mt-4">
                        <button type="submit" class="btn btn-primary">Update Password</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>

<footer>
    <jsp:include page="/WEB-INF/layout/footer.jsp"/>
</footer>

</body>
</html>
