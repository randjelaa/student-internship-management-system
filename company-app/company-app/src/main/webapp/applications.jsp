<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.dto.*" %>
<%@ page import="com.example.service.ApplicationService" %>

<%
    LoginResponse user = (LoginResponse) request.getSession().getAttribute("user");
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    String message = null;
    ApplicationService service = new ApplicationService();

    List<InternshipApplicationsGroupDTO> groups = null;
    try {
        groups = service.getGrouped(request);
    } catch (Exception e) {
        message = "Error in getting applications";
        e.printStackTrace();
    }

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String action = request.getParameter("action");
        String appId = request.getParameter("applicationId");

        try {
            if (action == null || appId == null) {
                message = "Invalid action";
            } else {
                Long id = Long.parseLong(appId);

                if ("accept".equals(action)) {
                    service.accept(id, request);

                    response.sendRedirect("applications.jsp");
                    return;
                } else if ("reject".equals(action)) {
                    service.reject(id, request);

                    response.sendRedirect("applications.jsp");
                    return;
                } else {
                    message = "Unknown action";
                }
            }
        } catch (Exception e) {
            message = "Error in accepting/rejecting application";
            e.printStackTrace();
        }
    }
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Applications</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/applications.css">
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/layout/header.jsp"/>
</header>

<main>
    <div class="container">
        <h2 class="mb-4">Applications</h2>

        <% if (message != null) { %>
        <div class="alert alert-success"><%= message %>
        </div>
        <% } %>

        <% if (groups != null) {
            for (InternshipApplicationsGroupDTO group : groups) { %>

        <div class="app-card-container">
            <h5 class="text-primary mt-4"><%= group.getInternshipTitle() %>
            </h5>

            <table class="table align-middle">
                <thead>
                <tr>
                    <th>Student</th>
                    <th>Status</th>
                    <th>Applied</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (CompanyApplicationViewDTO app : group.getApplications()) { %>
                <tr>
                    <td data-label="Student"><strong><%= app.getStudentFullName() %>
                    </strong></td>
                    <td data-label="Status">
                                <span class="badge bg-<%= app.getStatus().equals("PENDING") ? "warning" : "secondary" %>">
                                    <%= app.getStatus() %>
                                </span>
                    </td>
                    <td data-label="Applied"><%= app.getAppliedAt() %>
                    </td>
                    <td data-label="Actions">
                        <div class="btn-group-mobile">
                            <% if ("PENDING".equals(app.getStatus())) { %>
                            <form method="post" onsubmit="return confirmAction('accept')" class="m-0">
                                <input type="hidden" name="action" value="accept"/>
                                <input type="hidden" name="applicationId" value="<%= app.getApplicationId() %>"/>
                                <button type="submit" class="btn btn-sm btn-success">Accept</button>
                            </form>
                            <form method="post" onsubmit="return confirmAction('reject')" class="m-0">
                                <input type="hidden" name="action" value="reject"/>
                                <input type="hidden" name="applicationId" value="<%= app.getApplicationId() %>"/>
                                <button type="submit" class="btn btn-sm btn-outline-danger">Reject</button>
                            </form>
                            <% } %>
                            <a href="cv.jsp?studentId=<%= app.getStudentId() %>" class="btn btn-sm btn-primary">View CV</a>
                        </div>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <% }
        } %>
    </div>
</main>

<footer>
    <jsp:include page="/WEB-INF/layout/footer.jsp"/>
</footer>

</body>
</html>