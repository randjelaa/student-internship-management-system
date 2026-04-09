<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.GradeDetails" %>
<%@ page import="com.example.dto.WorkLogResponseDTO" %>

<html>
<head>
    <title>Grades</title>
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
    <%= facultyGrade %> ✅
</p>

<% } else { %>

<form method="post" action="grades">
    <input type="hidden" name="gradeId" value="<%= g.getId() %>"/>

    <label>
        Faculty's grade:
        <input type="number" name="facultyGrade" min="6" max="10" required/>
    </label>

    <button type="submit">Save</button>
</form>

<%
            }
        }
    }
%>

<jsp:include page="layout/footer.jsp"/>
</body>
</html>
