<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page import="com.example.service.GradeService" %>
<%@ page import="com.example.dto.InternshipGradingGroupDTO" %>
<%@ page import="com.example.dto.StudentGradingDetailDTO" %>
<%@ page import="com.example.dto.WorkLogResponseDTO" %>

<%
    LoginResponse user = (LoginResponse) request.getSession().getAttribute("user");
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    String message = null;
    GradeService service = new GradeService();

    InternshipGradingGroupDTO[] groups = null;
    try {
        groups = service.getDashboard(request);
    } catch (Exception e) {
        message = "Error loading dashboard";
        e.printStackTrace();
    }

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        try {
            Long studentId = Long.parseLong(request.getParameter("studentId"));
            Long internshipId = Long.parseLong(request.getParameter("internshipId"));
            String comment = request.getParameter("comment");

            if (comment == null || comment.trim().isEmpty()) {
                message = "Comment is empty";
            } else {
                service.createGrade(studentId, internshipId, comment, request);

                response.sendRedirect("grades.jsp");
                return;
            }
        } catch (Exception e) {
            message = "Error creating grade";
            e.printStackTrace();
        }
    }
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Grading dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/cards.css">

    <script>
        function validateGradeForm(form) {
            const comment = form.querySelector('textarea[name="comment"]').value;
            if (!comment || comment.trim() === "") {
                alert("Comment is required");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/layout/header.jsp"/>
</header>

<main>
    <div class="container">
        <h2 class="page-title">Grading dashboard</h2>

        <% if (message != null) { %>
        <div class="alert alert-success"><%= message %></div>
        <% } %>

        <% if (groups != null) {
            for (InternshipGradingGroupDTO group : groups) { %>

        <h5 class="section-title text-primary"><%= group.getInternshipTitle() %></h5>

        <% for (StudentGradingDetailDTO student : group.getStudents()) { %>
        <div class="grading-card p-3 mb-4">
            <strong><%= student.getStudentFullName() %></strong>

            <div class="mb-3">
                <p class="d-md-none fw-bold text-secondary text-uppercase" style="font-size: 0.7rem;">Work Logs:</p>
                <% for (WorkLogResponseDTO log : student.getWorkLogs()) { %>
                <div class="worklog-item">
                    <div class="fw-bold" style="font-size: 0.75rem;"><%= log.getStartDate() %> - <%= log.getEndDate() %></div>
                    <div class="text-secondary"><%= log.getDescription() %></div>
                </div>
                <% } %>
            </div>

            <form method="post" action="grades.jsp" onsubmit="return validateGradeForm(this)" class="mt-3">
                <input type="hidden" name="studentId" value="<%= student.getStudentId() %>"/>
                <input type="hidden" name="internshipId" value="<%= group.getInternshipId() %>"/>

                <div class="mb-2">
                    <label class="small fw-bold mb-1">Final comment:</label>
                    <% if (student.isGraded()) { %>
                    <div class="p-2 bg-light border rounded small text-muted"><%= student.getExistingComment() %></div>
                    <div class="d-grid mt-3">
                        <button class="btn btn-sm btn-secondary" disabled>Already graded</button>
                    </div>

                    <% } else { %>
                    <textarea name="comment" class="form-control form-control-sm" rows="3"
                              placeholder="Enter student performance review..." required></textarea>
                    <div class="d-grid mt-3">
                        <button type="submit" class="btn btn-primary">Submit grade</button>
                    </div>
                    <% } %>
                </div>
            </form>
        </div>
        <% } %>
        <% }
        } %>
    </div>
</main>

<footer>
    <jsp:include page="/WEB-INF/layout/footer.jsp"/>
</footer>

</body>
</html>
