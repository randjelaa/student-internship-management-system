<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.CompanyResponseDTO" %>

<%
    CompanyResponseDTO c = (CompanyResponseDTO) request.getAttribute("company");
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Company details</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cards.css">
</head>

<body>

<header>
    <jsp:include page="layout/header.jsp"/>
</header>

<main>
    <div class="container">

        <div class="mt-2 mb-3">
            <a href="companies" class="text-decoration-none small text-secondary">&larr; Back to companies</a>
        </div>

        <% if (c != null) { %>

        <h2 class="page-title text-primary mb-2"><%= c.getName() %></h2>

        <div class="text-muted mb-3">
            <%= c.getEmail() %>
        </div>

        <div class="grading-card p-3 mb-4">
            <div class="mb-3">
                <label class="small fw-bold">Description</label>
                <div class="text-secondary"><%= c.getDescription() %></div>
            </div>

            <div class="mb-3">
                <label class="small fw-bold">Website</label>
                <div>
                    <a href="<%= c.getWebsite() %>" target="_blank" class="text-decoration-none">
                        <%= c.getWebsite() %>
                    </a>
                </div>
            </div>

            <div class="mb-3">
                <label class="small fw-bold">Status</label>
                <div>
                    <% if (c.getActive()) { %>
                    <span class="badge bg-success">Active</span>
                    <% } else { %>
                    <span class="badge bg-secondary">Inactive</span>
                    <% } %>
                </div>
            </div>

            <form method="post" action="companies" class="mt-3">
                <input type="hidden" name="id" value="<%= c.getId() %>"/>

                <% if (c.getActive()) { %>
                <input type="hidden" name="action" value="deactivate"/>
                <button type="submit" class="btn btn-warning w-100">
                    Deactivate company
                </button>
                <% } else { %>
                <input type="hidden" name="action" value="activate"/>
                <button type="submit" class="btn btn-success w-100">
                    Activate company
                </button>
                <% } %>
            </form>

        </div>

        <% } else { %>

        <div class="alert alert-danger">
            Company not found
        </div>

        <% } %>

    </div>
</main>

<footer>
    <jsp:include page="layout/footer.jsp"/>
</footer>

</body>
</html>