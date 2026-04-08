<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.dto.LoginResponse" %>

<%
    LoginResponse user = (LoginResponse) request.getSession().getAttribute("user");
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }
%>

<div class="d-flex justify-content-between align-items-center p-3">
    <span class="fw-bold">Internship System</span>
    <div class="small">
        <%= user.getEmail() %>

        <form method="post" action="<%= request.getContextPath() %>/logout.jsp" class="d-inline ms-2">
            <button class="btn btn-sm btn-outline-dark" type="submit">Logout</button>
        </form>
    </div>
</div>
