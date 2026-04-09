<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.GradeDetails" %>
<%@ page import="com.example.dto.WorkLogResponseDTO" %>

<html>
<head>
    <title>Grades</title>

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
<jsp:include page="layout/header.jsp"/>

<h2>Grades</h2>

<%
    GradeDetails[] grades = (GradeDetails[]) request.getAttribute("grades");

    if (grades != null) {
        for (GradeDetails g : grades) {

            if (g.getCompanyComment() == null) continue;
%>

<hr/>

<h3>
    <%= g.getStudent().getFirstName() %>
    <%= g.getStudent().getLastName() %>
    (<%= g.getStudent().getIndexNumber() %>)
</h3>

<p>
    Internship: <b><%= g.getInternship().getTitle() %></b> - <%= g.getInternship().getCompanyName() %>
</p>

<h4>Work logs:</h4>

<table border="1">
    <tr>
        <th>Week</th>
        <th>Description</th>
    </tr>

    <%
        for (WorkLogResponseDTO w : g.getWorkLogs()) {
    %>
    <tr>
        <td><%= w.getStartDate() %> - <%= w.getEndDate()%></td>
        <td><%= w.getDescription() %></td>
    </tr>
    <%
        }
    %>

</table>

<br/>

<p>
    <b>Company's comment:</b><br/>
    <%= g.getCompanyComment() %>
</p>

<br/>

<%
    Integer facultyGrade = g.getFacultyGrade();
        if (facultyGrade != null) {
%>

<p>
    <b>Faculty's grade:</b>
    <%= facultyGrade %>
</p>

<% } else { %>

<form method="post" action="grades" onsubmit="return validateGrade(this)">
    <input type="hidden" name="gradeId" value="<%= g.getId() %>"/>

    <label>
        Faculty's grade:
        <input type="number" name="facultyGrade" min="6" max="10" step="1" required/>
    </label>

    <button type="submit">Save</button>
</form>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<div style="color:red;"><%= error %></div>
<%
    }
%>

<%
            }
        }
    }
%>

<jsp:include page="layout/footer.jsp"/>
</body>
</html>
