<%@ page import="com.example.util.AuthUtil" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.service.GradeService" %>
<%@ page import="com.example.dto.InternshipGradingGroupDTO" %>
<%@ page import="com.example.dto.StudentGradingDetailDTO" %>
<%@ page import="com.example.dto.WorkLogResponseDTO" %>
<%@ page import="com.example.dto.CreateGradeRequest" %>
<%@ page import="com.example.util.ApiClient" %>

<%
    LoginResponse user = AuthUtil.requireUser(request, response);
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    GradeService service = new GradeService();
    InternshipGradingGroupDTO[] groups = null;

    try {
        groups = service.getDashboard(request);
    } catch (Exception e) {
        e.printStackTrace();
    }

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        try {
            CreateGradeRequest body = new CreateGradeRequest();

            body.setStudentId(Long.parseLong(request.getParameter("studentId")));
            body.setInternshipId(Long.parseLong(request.getParameter("internshipId")));
            body.setCompanyComment(request.getParameter("comment"));
            body.setFacultyGrade(null);

            ApiClient.post("/grades", body, Object.class, request);

            response.sendRedirect("grades.jsp");
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>

<html>
<head>
    <title>Grading Dashboard</title>
</head>
<body>

<jsp:include page="WEB-INF/layout/header.jsp"/>

<h2>Grading Dashboard</h2>

<% if (groups != null) {
    for (InternshipGradingGroupDTO group : groups) { %>

<h3><%= group.getInternshipTitle() %>
</h3>

<% for (StudentGradingDetailDTO student : group.getStudents()) { %>

<div style="border:1px solid black; padding:10px; margin-bottom:20px;">

    <h4><%= student.getStudentFullName() %>
    </h4>

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

    <form method="post" action="grades.jsp">
        <input type="hidden" name="studentId" value="<%= student.getStudentId() %>"/>
        <input type="hidden" name="internshipId" value="<%= group.getInternshipId() %>"/>

        <label>Comment:</label><br/>

        <% if (student.isGraded()) { %>
        <textarea rows="3" cols="50" readonly>
        <%= student.getExistingComment() %>
        </textarea>

        <br/><br/>
        <button disabled>Already graded</button>

        <% } else { %>
        <textarea name="comment" rows="3" cols="50" required></textarea>

        <br/><br/>
        <button type="submit">Submit Grade</button>

        <% } %>
    </form>
</div>

<% } %>

<hr/>

<% }

} %>

<jsp:include page="WEB-INF/layout/footer.jsp"/>

</body>
</html>
