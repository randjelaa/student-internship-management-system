<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dto.InternshipResponseDTO" %>
<%@ page import="com.example.service.InternshipService" %>
<%@ page import="com.example.dto.LoginResponse" %>

<%
    LoginResponse user = (LoginResponse) request.getSession().getAttribute("user");
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    String message = null;
    InternshipService internshipService = new InternshipService();

    List<InternshipResponseDTO> internships = null;
    try {
        internships = internshipService.getMyInternships(request);
    } catch (Exception e) {
        message = "Error loading internships";
        e.printStackTrace();
    }

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                internshipService.delete(id, request);

                response.sendRedirect("internships.jsp");
                return;
            } catch (Exception e) {
                message = "Error deleting internship";
                e.printStackTrace();
            }
        }
    }
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Internships</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/applications.css">
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/layout/header.jsp"/>
</header>

<main>
    <div class="container pb-5">
        <div class="d-flex justify-content-between align-items-center my-3">
            <h2 class="h4 mb-0">My Internships</h2>
            <a href="create-update-internship.jsp" class="btn btn-sm btn-primary">+ New</a>
        </div>

        <% if (message != null) { %>
        <div class="alert alert-danger py-2 small"><%= message %></div>
        <% } %>

        <div class="table-responsive">
            <table class="table align-middle">
                <thead>
                <tr>
                    <th>Internship Details</th>
                    <th>Technologies</th>
                    <th class="text-end">Actions</th>
                </tr>
                </thead>

                <tbody>
                <% if (internships != null) {
                    for (InternshipResponseDTO i : internships) { %>
                <tr>
                    <td data-label="Internship">
                        <div class="fw-bold text-primary h6 mb-1"><%= i.getTitle() %></div>
                        <div class="small text-muted text-wrap"><%= i.getDescription() %></div>
                    </td>

                    <td>
                        <div class="small">
                            <span class="d-md-none fw-bold text-secondary text-uppercase" style="font-size: 0.7rem;">Location: </span>
                            <%= i.getLocation() %>
                        </div>
                    </td>

                    <td>
                        <div class="small">
                            <span class="d-md-none fw-bold text-secondary text-uppercase" style="font-size: 0.7rem;">Duration: </span>
                            <%= i.getStartDate() %> - <%= i.getEndDate() %>
                        </div>
                    </td>

                    <td data-label="Technologies">
                        <% if (i.getTechnologies() != null) {
                            for (String tech : i.getTechnologies()) { %>
                        <span class="badge bg-light text-dark border"><%= tech %></span>
                        <% }
                        } %>
                    </td>

                    <td data-label="Actions" class="text-end">
                        <div class="btn-group-mobile mt-2">
                            <button class="btn btn-sm btn-outline-secondary" onclick="window.location.href='create-update-internship.jsp?id=<%= i.getId() %>'">Edit</button>

                            <form action="internships.jsp" method="post" class="d-inline m-0">
                                <input type="hidden" name="id" value="<%= i.getId() %>"/>
                                <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('Are you sure?')">Delete</button>
                            </form>
                        </div>
                    </td>
                </tr>
                <% }
                } %>
                </tbody>
            </table>
        </div>
    </div>
</main>

<footer>
    <jsp:include page="/WEB-INF/layout/footer.jsp"/>
</footer>

</body>
</html>
