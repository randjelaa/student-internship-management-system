<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.InternshipResponseDTO" %>

<%
    InternshipResponseDTO i = (InternshipResponseDTO) request.getAttribute("internship");
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Internship details</title>

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
            <a href="internships" class="text-decoration-none small text-secondary">&larr; Back to internships</a>
        </div>

        <% if (i != null) { %>

        <h2 class="page-title text-primary mb-2"><%= i.getTitle() %></h2>

        <div class="text-muted mb-3">
            <%= i.getCompanyName() %> • <%= i.getLocation() %>
        </div>

        <div class="grading-card p-3 mb-4">
            <div class="mb-3">
                <label class="small fw-bold mb-1">Description</label>
                <div class="text-secondary">
                    <%= i.getDescription() %>
                </div>
            </div>

            <div class="mb-3">
                <label class="small fw-bold mb-1">Duration</label>
                <div class="text-secondary">
                    <%= i.getStartDate() %> - <%= i.getEndDate() %>
                </div>
            </div>

            <div class="mb-3">
                <label class="small fw-bold mb-1">Requirements</label>
                <div class="text-secondary">
                    <%= i.getRequirements() %>
                </div>
            </div>

            <div class="mb-2">
                <label class="small fw-bold mb-2">Technologies</label>
                <div>
                    <%
                        if (i.getTechnologies() != null) {
                            for (String tech : i.getTechnologies()) {
                    %>
                    <span class="badge bg-light text-dark border me-1 mb-1">
                            <%= tech %>
                        </span>
                    <%
                            }
                        }
                    %>
                </div>
            </div>

        </div>

        <% } else { %>

        <div class="alert alert-danger">
            Internship not found
        </div>

        <% } %>

    </div>
</main>

<footer>
    <jsp:include page="layout/footer.jsp"/>
</footer>

</body>
</html>