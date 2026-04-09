<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.util.PageResponse" %>

<html>
<head>
    <title>Internships</title>
</head>
<body>
<jsp:include page="layout/header.jsp"/>

<h2>Internships</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Company</th>
        <th>Location</th>
    </tr>

    <%
        PageResponse pageData = (PageResponse) request.getAttribute("page");
        List content = pageData.getContent();

        for (Object obj : content) {
            Map item = (Map) obj;
    %>

    <tr>
        <td><%= item.get("id") %></td>
        <td><%= item.get("title") %></td>
        <td><%= item.get("companyName") %></td>
        <td><%= item.get("location") %></td>
    </tr>

    <%
        }
    %>

</table>

<br/>

<%
    int currentPage = (Integer) request.getAttribute("currentPage");
    if (currentPage > 0) {
%>
<a href="internships?page=<%= currentPage - 1 %>">Previous</a>
<% } %>
|
<a href="internships?page=<%= currentPage + 1 %>">Next</a>

<jsp:include page="layout/footer.jsp"/>
</body>
</html>
