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
    <title>Grading Dashboard</title>

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

<jsp:include page="WEB-INF/layout/header.jsp"/>

<h2>Grading Dashboard</h2>

<%
    if (message != null) {
%>
<p style="color:green;"><%= message %></p>
<%
    }
%>

<% if (groups != null) {
    for (InternshipGradingGroupDTO group : groups) { %>

<h3><%= group.getInternshipTitle() %></h3>

<% for (StudentGradingDetailDTO student : group.getStudents()) { %>
<div style="border:1px solid black; padding:10px; margin-bottom:20px;">
    <h4><%= student.getStudentFullName() %></h4>

    <table border="1" cellpadding="5">
        <tr>
            <th>Period</th>
            <th>Description</th>
        </tr>

        <% for (WorkLogResponseDTO log : student.getWorkLogs()) { %>
        <tr>
            <td>
                <%= log.getStartDate() %> - <%= log.getEndDate() %>
            </td>
            <td><%= log.getDescription() %>
            </td>
        </tr>
        <% } %>
    </table>

    <br/>

    <form method="post" action="grades.jsp" onsubmit="return validateGradeForm(this)">
        <input type="hidden" name="studentId" value="<%= student.getStudentId() %>"/>
        <input type="hidden" name="internshipId" value="<%= group.getInternshipId() %>"/>

        <label>Comment:</label><br/>

        <% if (student.isGraded()) { %>
        <label>
            <textarea rows="3" cols="50" readonly><%= student.getExistingComment() %></textarea>
        </label>

        <br/><br/>
        <button disabled>Already graded</button>

        <% } else { %>
        <label>
            <textarea name="comment" rows="3" cols="50" required></textarea>
        </label>

        <br/><br/>
        <button type="submit">Submit Grade</button>

        <% } %>
    </form>
</div>
<% } %>
<hr/>
<%
    }
}
%>

<jsp:include page="WEB-INF/layout/footer.jsp"/>

</body>
</html>
