<%@ page import="com.example.util.AuthUtil" %>
<%@ page import="com.example.dto.LoginResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.*" %>
<%@ page import="com.example.dto.*" %>
<%@ page import="com.example.service.ApplicationService" %>
<%@ page import="com.example.util.AuthUtil" %>

<%
    LoginResponse user = AuthUtil.requireUser(request, response);
    if (user == null) return;

    ApplicationService service = new ApplicationService();

    // 🔥 HANDLE ACTIONS (accept/reject)
    String action = request.getParameter("action");
    String appId = request.getParameter("applicationId");

    if (action != null && appId != null) {
        try {
            Long id = Long.parseLong(appId);

            if ("accept".equals(action)) {
                service.accept(id, request);
            } else if ("reject".equals(action)) {
                service.reject(id, request);
            }

            response.sendRedirect("applications.jsp");
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<InternshipApplicationsGroupDTO> groups = null;

    try {
        groups = service.getGrouped(request);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<html>
<head>
    <title>Applications</title>
</head>
<body>

<jsp:include page="/WEB-INF/layout/header.jsp"/>

<h2>Applications by Internship</h2>

<%
    if (groups != null) {
        for (InternshipApplicationsGroupDTO group : groups) {
%>

<h3><%= group.getInternshipTitle() %></h3>

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
            <!-- ACCEPT -->
            <a href="applications.jsp?action=accept&applicationId=<%= app.getApplicationId() %>">
                Accept
            </a>

            |

            <!-- REJECT -->
            <a href="applications.jsp?action=reject&applicationId=<%= app.getApplicationId() %>">
                Reject
            </a>

            |

            <!-- CV PDF -->
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