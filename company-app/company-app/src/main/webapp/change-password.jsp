<%@ page import="com.example.util.AuthUtil" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.util.ApiClient" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page import="com.example.dto.ChangePasswordRequest" %>

<%
    LoginResponse user = AuthUtil.requireUser(request, response);
    if (user == null) return;

    String message = null;
    String error = null;

    if ("POST".equalsIgnoreCase(request.getMethod())) {

        String current = request.getParameter("currentPassword");
        String newPass = request.getParameter("newPassword");
        String confirm = request.getParameter("confirmPassword");

        if (!newPass.equals(confirm)) {
            error = "New passwords do not match";
        } else {

            try {
                ChangePasswordRequest body = new ChangePasswordRequest();
                body.setCurrentPassword(current);
                body.setNewPassword(newPass);

                ApiClient.post("/auth/change-password", body, Object.class, request);

                message = "Password changed successfully";

            } catch (Exception e) {
                error = "Failed to change password";
            }
        }
    }
%>

<html>
<head>
    <title>Change Password</title>
</head>
<body>

<jsp:include page="WEB-INF/layout/header.jsp"/>

<h2>Change Password</h2>

<p><b>Email:</b> <%= user.getEmail() %></p>

<% if (message != null) { %>
<p style="color:green;"><%= message %></p>
<% } %>

<% if (error != null) { %>
<p style="color:red;"><%= error %></p>
<% } %>

<form method="post">

    <label>Current password:</label><br/>
    <input type="password" name="currentPassword" required/><br/><br/>

    <label>New password:</label><br/>
    <input type="password" name="newPassword" required/><br/><br/>

    <label>Confirm new password:</label><br/>
    <input type="password" name="confirmPassword" required/><br/><br/>

    <button type="submit">Change Password</button>

</form>

<jsp:include page="WEB-INF/layout/footer.jsp"/>

</body>
</html>
