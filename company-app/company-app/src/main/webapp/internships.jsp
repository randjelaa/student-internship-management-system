<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dto.InternshipResponseDTO" %>
<%@ page import="com.example.service.InternshipService" %>
<%@ page import="com.example.dto.LoginResponse" %>

<%
    LoginResponse user = (LoginResponse) request.getSession().getAttribute("user");
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    String message = null;
    InternshipService internshipService = new InternshipService();

    List<InternshipResponseDTO> internships = null;
    try {
        internships = internshipService.getMyInternships(request);
    } catch (Exception e) {
        message = "Error loading internships";
        e.printStackTrace();
    }

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                internshipService.delete(id, request);

                response.sendRedirect("internships.jsp");
                return;
            } catch (Exception e) {
                message = "Error deleting internship";
                e.printStackTrace();
            }
        }
    }
%>

<html>
<head>
    <title>My Internships</title>
</head>
<body>

<jsp:include page="/WEB-INF/layout/header.jsp"/>

<h2>My Internships</h2>

<% if (message != null) { %>
<p style="color:red;"><%= message %></p>
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
        <td><%= i.getStartDate() %> - <%= i.getEndDate() %></td>
        <td><%= i.getRequirements() %></td>
        <td>
            <button onclick="window.location.href='create-update-internship.jsp?id=<%= i.getId() %>'">
                Update
            </button>

            <form action="internships.jsp" method="post" style="display:inline;">
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
