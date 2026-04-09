<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.dto.CompanySummaryDTO" %>

<html>
<head>
    <title>Companies</title>

    <script>
        function validateForm() {
            let email = document.getElementsByName("email")[0].value;
            let password = document.getElementsByName("password")[0].value;
            let name = document.getElementsByName("name")[0].value;
            let website = document.getElementsByName("website")[0].value;

            if (!email || !email.includes("@")) {
                alert("Invalid email");
                return false;
            }
            if (!password || password.length < 6) {
                alert("Password must be at least 6 characters");
                return false;
            }
            if (!name || name.trim() === "") {
                alert("Name is required");
                return false;
            }
            if (website && !website.startsWith("http")) {
                alert("Website must start with http");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
<jsp:include page="layout/header.jsp"/>

<h2>Companies</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>

    <%
        CompanySummaryDTO[] companies = (CompanySummaryDTO[]) request.getAttribute("companies");

        if (companies != null) {
            for (CompanySummaryDTO c : companies) {
    %>

    <tr onclick="window.location='companies?id=<%= c.getId() %>'" style="cursor:pointer;">
        <td><%= c.getId() %></td>
        <td><%= c.getName() %></td>
        <td><%= c.isActive() ? "Active" : "Inactive" %></td>

        <td>
            <form method="post" action="companies" style="display:inline;">
                <input type="hidden" name="id" value="<%= c.getId() %>"/>

                <% if (c.isActive()) { %>
                <input type="hidden" name="action" value="deactivate"/>
                <button type="submit">Deactivate</button>
                <% } else { %>
                <input type="hidden" name="action" value="activate"/>
                <button type="submit">Activate</button>
                <% } %>
            </form>
        </td>
    </tr>

    <%
            }
        }
    %>

</table>

<br/>

<h3>Add company</h3>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) { %>
<div style="color:red; margin-bottom:10px;"><%= error %></div>
<% } %>

<form method="post" action="companies" onsubmit="return validateForm()">
    <input type="hidden" name="action" value="create"/>

    <div>
        <label>
            Email:
            <input type="text" name="email"/>
        </label>
    </div>

    <div>
        <label>
            Password:
            <input type="password" name="password"/>
        </label>
    </div>

    <div>
        <label>
            Name:
            <input type="text" name="name"/>
        </label>
    </div>

    <div>
        <label>
            Description:
            <input type="text" name="description"/>
        </label>
    </div>

    <div>
        <label>
            Website:
            <input type="text" name="website"/>
        </label>
    </div>

    <button type="submit">Add</button>
</form>

<jsp:include page="layout/footer.jsp"/>
</body>
</html>
