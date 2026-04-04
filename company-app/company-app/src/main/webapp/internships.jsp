<%@ page import="com.example.util.AuthUtil" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.dto.InternshipResponseDTO" %>
<%@ page import="com.example.service.InternshipService" %>
<%@ page import="com.example.util.AuthUtil" %>
<%@ page import="com.example.dto.LoginResponse" %>

<%
    LoginResponse user = AuthUtil.requireUser(request, response);
    if (user == null) request.getRequestDispatcher("login.jsp").forward(request, response);

    InternshipService internshipService = new InternshipService();
    List<InternshipResponseDTO> internships = null;
    String error = null;

    try {
        internships = internshipService.getMyInternships(request);
    } catch (Exception e) {
        e.printStackTrace();
        error = "Error loading internships";
    }
%>

<html>
<head>
    <title>My Internships</title>
</head>
<body>

<jsp:include page="/WEB-INF/layout/header.jsp"/>

<h2>My Internships</h2>

<% if (error != null) { %>
<p style="color:red;"><%= error %></p>
<% } %>

<table border="1" cellpadding="10">
    <tr>
        <th>Title</th>
        <th>Description</th>
        <th>Technologies</th>
        <th>Location</th>
        <th>Period</th>
        <th>Requirements</th>
        <th>Actions</th>
    </tr>

    <%
        if (internships != null) {
            for (InternshipResponseDTO i : internships) {
    %>
    <tr>
        <td><%= i.getTitle() %></td>
        <td><%= i.getDescription() %></td>
        <td>
            <%
                if (i.getTechnologies() != null) {
                    for (String tech : i.getTechnologies()) {
            %>
            <span><%= tech %></span><br/>
            <%
                    }
                }
            %>
        </td>
        <td><%= i.getLocation() %></td>
        <td>
            <%= i.getStartDate() %> - <%= i.getEndDate() %>
        </td>
        <td><%= i.getRequirements() %></td>
        <td>
            <button onclick="window.location.href='create-update-internship.jsp?id=<%= i.getId() %>'">
                Update
            </button>

            <form action="delete-internship.jsp" method="post" style="display:inline;">
                <input type="hidden" name="id" value="<%= i.getId() %>"/>
                <button type="submit" onclick="return confirm('Are you sure?')">
                    Delete
                </button>
            </form>
        </td>
    </tr>
    <%
            }
        }
    %>

</table>

<br/><br/>

<button onclick="window.location.href='create-update-internship.jsp'">
    Create Internship
</button>

<jsp:include page="WEB-INF/layout/footer.jsp"/>

</body>
</html>
