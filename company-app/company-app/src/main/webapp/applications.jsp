<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.dto.*" %>
<%@ page import="com.example.service.ApplicationService" %>

<%
    LoginResponse user = (LoginResponse) request.getSession().getAttribute("user");
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    String message = null;
    ApplicationService service = new ApplicationService();

    List<InternshipApplicationsGroupDTO> groups = null;
    try {
        groups = service.getGrouped(request);
    } catch (Exception e) {
        message = "Error in getting applications";
        e.printStackTrace();
    }

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String action = request.getParameter("action");
        String appId = request.getParameter("applicationId");

        try {
            if (action == null || appId == null) {
                message = "Invalid action";
            } else {
                Long id = Long.parseLong(appId);

                if ("accept".equals(action)) {
                    service.accept(id, request);

                    response.sendRedirect("applications.jsp");
                    return;
                } else if ("reject".equals(action)) {
                    service.reject(id, request);

                    response.sendRedirect("applications.jsp");
                    return;
                } else {
                    message = "Unknown action";
                }
            }
        } catch (Exception e) {
            message = "Error in accepting/rejecting application";
            e.printStackTrace();
        }
    }
%>

<html>
<head>
    <title>Applications</title>
</head>

<script>
    function confirmAction(action) {
        return confirm("Are you sure you want to " + action + " this application?");
    }
</script>
<body>

<jsp:include page="/WEB-INF/layout/header.jsp"/>

<h2>Applications by Internship</h2>

<%
    if (message != null) {
%>
<p style="color:green;"><%= message %></p>
<%
    }
%>

<%
    if (groups != null) {
        for (InternshipApplicationsGroupDTO group : groups) {
%>

<h3><%= group.getInternshipTitle() %>
</h3>

<table border="1" cellpadding="10">
    <tr>
        <th>Student</th>
        <th>Status</th>
        <th>Applied At</th>
        <th>Actions</th>
    </tr>

    <%
        for (CompanyApplicationViewDTO app : group.getApplications()) {
    %>
    <tr>
        <td><%= app.getStudentFullName() %></td>
        <td><%= app.getStatus() %></td>
        <td><%= app.getAppliedAt() %></td>

        <td>
            <% if (!"PENDING".equals(app.getStatus())) { %>
            <button disabled>Accept</button>
            <button disabled>Reject</button>
            <% } else { %>
            <form method="post" action="applications.jsp" style="display:inline;"
                  onsubmit="return confirmAction('accept')">
                <input type="hidden" name="action" value="accept"/>
                <input type="hidden" name="applicationId" value="<%= app.getApplicationId() %>"/>
                <button type="submit">Accept</button>
            </form>

            <form method="post" action="applications.jsp" style="display:inline;"
                  onsubmit="return confirmAction('reject')">
                <input type="hidden" name="action" value="reject"/>
                <input type="hidden" name="applicationId" value="<%= app.getApplicationId() %>"/>
                <button type="submit">Reject</button>
            </form>
            <% } %>

            <a href="cv.jsp?studentId=<%= app.getStudentId() %>">
                View CV
            </a>
        </td>
    </tr>
    <%
        }
    %>
</table>

<br/><br/>

<%
        }
    }
%>

<jsp:include page="/WEB-INF/layout/footer.jsp"/>

</body>
</html>