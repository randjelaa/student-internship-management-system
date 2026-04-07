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
    <title>Change Password</title>

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

<jsp:include page="WEB-INF/layout/header.jsp"/>

<h2>Change Password</h2>

<p><b>Email:</b> <%= user.getEmail() %>
</p>

<%
    if (message != null) {
%>
<p style="color:green;"><%= message %></p>
<%
    }
%>

<form method="post" onsubmit="return validateForm()">
    <label>
        Current password:<br/>
        <input type="password" name="currentPassword" required/>
    </label>

    <br/><br/>

    <label>
        New password:<br/>
        <input type="password" name="newPassword" required/>
    </label>

    <br/><br/>

    <label>
        Confirm new password:<br/>
        <input type="password" name="confirmPassword" required/>
    </label>

    <br/><br/>

    <button type="submit">Change Password</button>
</form>

<jsp:include page="WEB-INF/layout/footer.jsp"/>

</body>
</html>
