<%@ page import="com.example.util.AuthUtil" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.service.GradeService" %>
<%@ page import="com.example.dto.InternshipGradingGroupDTO" %>
<%@ page import="com.example.dto.StudentGradingDetailDTO" %>
<%@ page import="com.example.dto.WorkLogResponseDTO" %>

<%
    LoginResponse user = AuthUtil.requireUser(request, response);
    if (user == null) return;

    GradeService service = new GradeService();
    InternshipGradingGroupDTO[] groups = null;

    try {
        groups = service.getDashboard(request);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<%
    if ("POST".equalsIgnoreCase(request.getMethod())) {

        String studentId = request.getParameter("studentId");
        String internshipId = request.getParameter("internshipId");
        String comment = request.getParameter("comment");

        try {
            // TODO: pozovi API za ocjenjivanje
            // ApiClient.post("/grades", requestBody, ...)

            response.sendRedirect("grading.jsp");
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

<% if (groups != null) { %>

<% for (InternshipGradingGroupDTO group : groups) { %>

<h3><%= group.getInternshipTitle() %></h3>

<% for (StudentGradingDetailDTO student : group.getStudents()) { %>

<div style="border:1px solid black; padding:10px; margin-bottom:20px;">

    <h4><%= student.getStudentFullName() %></h4>

    <!-- WORK LOG TABLE -->
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
            <td><%= log.getDescription() %></td>
        </tr>
        <% } %>
    </table>

    <br/>

    <!-- COMMENT FORM -->
    <form method="post" action="grading.jsp">
        <input type="hidden" name="studentId" value="<%= student.getStudentId() %>" />
        <input type="hidden" name="internshipId" value="<%= group.getInternshipId() %>" />

        <label>Comment:</label><br/>
        <textarea name="comment" rows="3" cols="50">
<%= student.getExistingComment() != null ? student.getExistingComment() : "" %>
                    </textarea>

        <br/><br/>

        <button type="submit">
            <%= student.isGraded() ? "Update Grade" : "Submit Grade" %>
        </button>
    </form>

</div>

<% } %>

<hr/>

<% } %>

<% } %>

<jsp:include page="WEB-INF/layout/footer.jsp"/>

</body>
</html>
