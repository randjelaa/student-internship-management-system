<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.dto.LoginResponse" %>

<%
    LoginResponse user = (LoginResponse) request.getSession().getAttribute("user");
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }
%>

<div style="display:flex; justify-content:space-between; border-bottom:1px solid black; padding:10px;">
    <div>
        <b>Internship System</b>
    </div>

    <div>
        <%= user.getEmail() %>

        <form method="post" action="<%= request.getContextPath() %>/logout.jsp" style="display:inline;">
            <button type="submit" onclick="return confirm('Logout?')">Logout</button>
        </form>
    </div>
</div>
