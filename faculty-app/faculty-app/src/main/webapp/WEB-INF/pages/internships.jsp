<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.InternshipSummaryDTO" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Internships</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cards.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tables.css">
</head>

<body>

<header>
    <jsp:include page="layout/header.jsp"/>
</header>

<main>
    <div class="container">

        <h2 class="page-title">Internships</h2>

        <table class="table table-hover align-middle">
            <thead class="table-light">
            <tr>
                <th>Title</th>
                <th>Company</th>
                <th>Location</th>
            </tr>
            </thead>

            <tbody>
            <%
                InternshipSummaryDTO[] internships =
                        (InternshipSummaryDTO[]) request.getAttribute("internships");

                if (internships != null) {
                    for (InternshipSummaryDTO i : internships) {
            %>

            <tr style="cursor:pointer;"
                onclick="window.location='internships?id=<%= i.getId() %>'">

                <td data-label="Title"><%= i.getTitle() %></td>
                <td data-label="Company"><%= i.getCompanyName() %></td>
                <td data-label="Location"><%= i.getLocation() %></td>
            </tr>

            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</main>

<footer>
    <jsp:include page="layout/footer.jsp"/>
</footer>

</body>
</html>