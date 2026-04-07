<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.*" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="com.example.service.CvService" %>

<%
    LoginResponse user = (LoginResponse) request.getSession().getAttribute("user");
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    String message = null;
    CvService service = new CvService();

    String action = request.getParameter("action");
    String studentIdParam = request.getParameter("studentId");

    if (action == null) action = "view";

    CvResponseDTO cv = null;
    Long studentId = null;
    try {
        if (studentIdParam == null) {
            message = "Student ID is required";
        } else {
            studentId = Long.parseLong(studentIdParam);
        }
    } catch (Exception e) {
        message = "Invalid student ID";
    }

    try {
        if (message == null) {
            if ("download".equals(action)) {
                byte[] pdf = service.getPdf(studentId, request);

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=cv.pdf");

                OutputStream os = response.getOutputStream();
                os.write(pdf);
                os.flush();
                return;
            }

            if ("photo".equals(action)) {
                byte[] image = service.getPhoto(studentId, request);

                response.setContentType("image/*");

                OutputStream os = response.getOutputStream();
                os.write(image);
                os.flush();
                return;
            }

            cv = service.getByStudentId(studentId, request);

            if (cv == null) {
                message = "CV not found";
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        message = "Error loading CV";
    }
%>

<html>
<head>
    <title>CV</title>
</head>
<body>

<jsp:include page="WEB-INF/layout/header.jsp"/>

<h2>CV</h2>

<%
    if (message != null) {
%>
<p style="color:green;"><%= message %></p>
<%
    }
%>

<%
    if (cv != null) {
%>

<p><b>Summary:</b> <%= cv.getSummary() %></p>

<%
    if (cv.getPhotoUrl() != null) {
%>
<img src="cv.jsp?action=photo&studentId=<%= studentId %>" width="150" alt="photo"/><%
    }
%>

<h3>Education</h3>
<ul>
    <%
        for (EducationDTO edu : cv.getEducations()) {
    %>
    <li>
        <b><%= edu.getInstitution() %>
        </b> -
        <%= edu.getDegree() %> (<%= edu.getFieldOfStudy() %>)
        [<%= edu.getStartYear() %> - <%= edu.getEndYear() %>]
    </li>
    <%
        }
    %>
</ul>

<h3>Experience</h3>
<ul>
    <%
        for (ExperienceDTO exp : cv.getExperiences()) {
    %>
    <li>
        <b><%= exp.getCompanyName() %></b> - <%= exp.getPosition() %><br/>
        <i><%= exp.getStartDate() %> - <%= exp.getEndDate() %></i><br/>
        <%= exp.getDescription() %>
    </li>
    <%
        }
    %>
</ul>

<h3>Skills</h3>
<ul>
    <%
        for (SkillDTO skill : cv.getSkills()) {
    %>
    <li><%= skill.getSkillName() %> - <%= skill.getSkillLevel() %></li>
    <%
        }
    %>
</ul>

<h3>Languages</h3>
<ul>
    <%
        for (LanguageDTO lang : cv.getLanguages()) {
    %>
    <li><%= lang.getLanguageName() %> - <%= lang.getLevel() %></li>
    <%
        }
    %>
</ul>

<h3>Interests</h3>
<ul>
    <%
        for (InterestDTO i : cv.getInterests()) {
    %>
    <li><%= i.getInterestName() %></li>
    <%
        }
    %>
</ul>

<a href="cv.jsp?action=download&studentId=<%= studentId %>">
    Download CV
</a>

<%
    }
%>

<br/><br/>
<a href="applications.jsp">
    <button type="button">Back</button>
</a>

<jsp:include page="WEB-INF/layout/footer.jsp"/>

</body>
</html>
