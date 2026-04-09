<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.dto.LoginResponse" %>

<%
    LoginResponse user = (LoginResponse) session.getAttribute("user");
%>

<div style="display:flex; justify-content:space-between; border-bottom:1px solid black; padding:10px;">
    <div>
        <b>Internship System</b>
    </div>

    <div>
        <%= user != null ? user.getEmail() : "" %>

        <form method="post" action="logout" style="display:inline;">
            <button type="submit">Logout</button>
        </form>
    </div>
</div>
