<%@ page import="com.example.dto.GradeDetails" %>
<%@ page import="com.example.dto.WorkLogResponseDTO" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/29/2026
  Time: 9:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reports</title>
</head>
<body>
<jsp:include page="layout/header.jsp"/>

<h2>Praćenje rada studenata</h2>

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
    Praksa: <b><%= g.getInternship().getTitle() %></b>
    - <%= g.getInternship().getCompanyName() %>
</p>

<!-- WORK LOG -->
<h4>Dnevnik rada</h4>

<table border="1">
    <tr>
        <th>Sedmica</th>
        <th>Opis</th>
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

<!-- KOMENTAR -->
<p>
    <b>Komentar kompanije:</b><br/>
    <%= g.getCompanyComment() %>
</p>

<br/>

<!-- OCJENA -->
<%
    Integer facultyGrade = g.getFacultyGrade();
%>

<% if (facultyGrade != null) { %>

<p>
    <b>Ocjena fakulteta:</b>
    <%= facultyGrade %> ✅
</p>

<% } else { %>

<form method="post" action="grades">

    <input type="hidden" name="gradeId" value="<%= g.getId() %>"/>

    Ocjena fakulteta:
    <input type="number" name="facultyGrade" min="6" max="10" required/>

    <button type="submit">Sačuvaj</button>

</form>

<% } %>

<%
        }
    }
%>

<jsp:include page="layout/footer.jsp"/>
</body>
</html>
