<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.InternshipSummaryDTO" %>

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
        InternshipSummaryDTO[] internships =
                (InternshipSummaryDTO[]) request.getAttribute("internships");

        if (internships != null) {
            for (InternshipSummaryDTO i : internships) {
    %>

    <tr>
        <td><%= i.getId() %></td>
        <td><%= i.getTitle() %></td>
        <td><%= i.getCompanyName() %></td>
        <td><%= i.getLocation() %></td>
    </tr>

    <%
            }
        }
    %>

</table>

<br/>

<jsp:include page="layout/footer.jsp"/>
</body>
</html>
