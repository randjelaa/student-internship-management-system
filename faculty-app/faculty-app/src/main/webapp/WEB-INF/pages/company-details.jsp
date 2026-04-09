<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.CompanyResponseDTO" %>

<html>
<head>
    <title>Company details</title>
</head>

<body>
<jsp:include page="layout/header.jsp"/>

<h2>Company details</h2>

<%
    CompanyResponseDTO c = (CompanyResponseDTO) request.getAttribute("company");
%>

<div>
    <b>ID:</b> <%= c.getId() %>
</div>

<div>
    <b>Email:</b> <%= c.getEmail() %>
</div>

<div>
    <b>Name:</b> <%= c.getName() %>
</div>

<div>
    <b>Description:</b> <%= c.getDescription() %>
</div>

<div>
    <b>Website:</b> <%= c.getWebsite() %>
</div>

<div>
    <b>Status:</b>
    <%= c.getActive() ? "Active" : "Inactive" %>
</div>

<form method="post" action="companies">
    <input type="hidden" name="id" value="<%= c.getId() %>"/>

    <% if (c.getActive()) { %>
    <input type="hidden" name="action" value="deactivate"/>
    <button type="submit">Deactivate</button>
    <% } else { %>
    <input type="hidden" name="action" value="activate"/>
    <button type="submit">Activate</button>
    <% } %>
</form>

<br/>

<a href="companies">Back</a>

<jsp:include page="layout/footer.jsp"/>
</body>
</html>
