<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.dto.LoginResponse" %>

<%
    LoginResponse user = (LoginResponse) session.getAttribute("user");
%>

<div class="d-flex justify-content-between align-items-center px-2 py-2">
    <div class="d-flex flex-column">
        <span class="fw-bold">Internship System</span>
        <span class="small">User: <%= user.getEmail() %></span>
    </div>

    <form method="post" action="logout" class="m-0">
        <button class="btn btn-sm btn-outline-dark" type="submit">Logout</button>
    </form>
</div>
