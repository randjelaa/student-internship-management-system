<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.GradeDetails" %>
<%@ page import="com.example.dto.WorkLogResponseDTO" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Grades</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cards.css">

    <script>
        function validateGrade(form) {
            let grade = form.facultyGrade.value;

            if (!grade) {
                alert("Grade is required");
                return false;
            }

            let num = parseInt(grade);

            if (num < 6 || num > 10) {
                alert("Grade must be between 6 and 10");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>

<header>
    <jsp:include page="layout/header.jsp"/>
</header>

<main>
    <div class="container">

        <h2 class="page-title">Grades</h2>

        <%
            GradeDetails[] grades = (GradeDetails[]) request.getAttribute("grades");

            if (grades != null) {
                for (GradeDetails g : grades) {

                    if (g.getCompanyComment() == null) continue;
        %>

        <div class="grading-card p-3 mb-4">
            <span class="fw-bold mb-1">
                <%= g.getStudent().getFirstName() %>
                <%= g.getStudent().getLastName() %>
            </span>

            <div class="text-muted small mb-2">
                <%= g.getStudent().getIndexNumber() %>
            </div>

            <div class="mb-3">
                <span class="fw-bold">Internship:</span>
                <span class="text-primary"><%= g.getInternship().getTitle() %></span>
                <span class="text-muted"> - <%= g.getInternship().getCompanyName() %></span>
            </div>

            <div class="mb-3">
                <p class="fw-bold text-secondary text-uppercase" style="font-size: 0.7rem;">
                    Work Logs:
                </p>

                <% for (WorkLogResponseDTO w : g.getWorkLogs()) { %>
                <div class="worklog-item">
                    <div class="fw-bold" style="font-size: 0.75rem;">
                        <%= w.getStartDate() %> - <%= w.getEndDate() %>
                    </div>
                    <div class="text-secondary">
                        <%= w.getDescription() %>
                    </div>
                </div>
                <% } %>
            </div>

            <div class="mb-3">
                <label class="small fw-bold mb-1">Company's comment:</label>
                <div class="p-2 bg-light border rounded small text-muted">
                    <%= g.getCompanyComment() %>
                </div>
            </div>

            <%
                Integer facultyGrade = g.getFacultyGrade();
                if (facultyGrade != null) {
            %>

            <div>
                <label class="small fw-bold mb-1">Faculty's grade:</label>
                <div class="p-2 bg-light border rounded small">
                    <%= facultyGrade %>
                </div>
            </div>

            <% } else { %>

            <form method="post" action="grades" onsubmit="return validateGrade(this)" class="mt-3">
                <input type="hidden" name="gradeId" value="<%= g.getId() %>"/>

                <div class="mb-2">
                    <label class="small fw-bold mb-1">Faculty's grade:</label>
                    <input type="number" name="facultyGrade"
                           class="form-control form-control-sm"
                           min="6" max="10" step="1" required/>
                </div>

                <div class="d-grid mt-2">
                    <button type="submit" class="btn btn-primary">
                        Save grade
                    </button>
                </div>
            </form>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <div class="alert alert-danger mt-2"><%= error %></div>
            <%
                }
            %>

            <% } %>

        </div>

        <%
                }
            }
        %>

    </div>
</main>

<footer>
    <jsp:include page="layout/footer.jsp"/>
</footer>

</body>
</html>
