<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.model.dto.CompanySummaryDTO" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <jsp:include page="layout/header.jsp"/>

    <h2>Companies</h2>

    <h3>Add company</h3>

    <form method="post" action="companies">
        <input type="hidden" name="action" value="create"/>

        <div>
            Email: <input type="text" name="email"/>
        </div>

        <div>
            Password: <input type="password" name="password"/>
        </div>

        <div>
            Naziv: <input type="text" name="name"/>
        </div>

        <div>
            Opis: <input type="text" name="description"/>
        </div>

        <div>
            Website: <input type="text" name="website"/>
        </div>

        <button type="submit">Dodaj</button>
    </form>

    <br/>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>

        <%
            CompanySummaryDTO[] companies =
                    (CompanySummaryDTO[]) request.getAttribute("companies");

            if (companies != null) {
                for (CompanySummaryDTO c : companies) {
        %>

        <tr>
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

    <jsp:include page="layout/footer.jsp"/>
</body>
</html>
