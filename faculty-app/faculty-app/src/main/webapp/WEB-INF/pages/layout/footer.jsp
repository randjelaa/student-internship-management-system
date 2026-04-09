<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String uri = request.getRequestURI();
%>

<div class="footer-nav d-flex justify-content-around">
    <a href="companies"
       class="<%= uri.contains("companies") ? "active" : "" %>">
        <span>Companies</span>
    </a>

    <a href="students"
       class="<%= uri.contains("students") ? "active" : "" %>">
        <span>Students</span>
    </a>

    <a href="internships"
       class="<%= uri.contains("internships") ? "active" : "" %>">
        <span>Internships</span>
    </a>

    <a href="grades"
       class="<%= uri.contains("grades") ? "active" : "" %>">
        <span>Grades</span>
    </a>
</div>

