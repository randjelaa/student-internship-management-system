<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String uri = request.getRequestURI();
%>

<div class="footer-nav d-flex justify-content-around">
    <a href="<%= request.getContextPath() %>/internships.jsp"
       class="<%= uri.contains("internships.jsp") ? "active" : "" %>">
        <span>Internships</span>
    </a>

    <a href="<%= request.getContextPath() %>/applications.jsp"
       class="<%= uri.contains("applications.jsp") ? "active" : "" %>">
        <span>Applications</span>
    </a>

    <a href="<%= request.getContextPath() %>/grades.jsp"
       class="<%= uri.contains("grades.jsp") ? "active" : "" %>">
        <span>Grades</span>
    </a>

    <a href="<%= request.getContextPath() %>/change-password.jsp"
       class="<%= uri.contains("change-password.jsp") ? "active" : "" %>">
        <span>Change password</span>
    </a>
</div>



