<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.util.ApiClient" %>
<%@ page import="com.example.dto.*" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="com.example.util.AuthUtil" %>

<%
    LoginResponse user = AuthUtil.requireUser(request, response);
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    String action = request.getParameter("action");
    String studentId = request.getParameter("studentId");
    CvResponseDTO cv = null;

    if (action == null) action = "view";

    try {
        cv = ApiClient.get("/cv/student/" + studentId, CvResponseDTO.class, request);

        if ("download".equals(action)) {
            byte[] pdf = ApiClient.getBytes("/cv/pdf/" + studentId, request);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=cv.pdf");

            OutputStream os = response.getOutputStream();
            os.write(pdf);
            os.flush();
            return;
        }

        if ("photo".equals(action)) {
            byte[] image = ApiClient.getBytes("/cv/photo/" + studentId, request);

            response.setContentType("image/*");

            OutputStream os = response.getOutputStream();
            os.write(image);
            os.flush();
            return;
        }

    } catch (Exception e) {
        e.printStackTrace();
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
    if (cv != null) {
%>

<p><b>Summary:</b> <%= cv.getSummary() %>
</p>

<%
    if (cv.getPhotoUrl() != null) {
%>
<img src="cv.jsp?action=photo&studentId=<%= studentId %>" width="150"/><%
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
        <b><%= exp.getCompanyName() %>
        </b> - <%= exp.getPosition() %><br/>
        <i><%= exp.getStartDate() %> - <%= exp.getEndDate() %>
        </i><br/>
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
    <li><%= skill.getSkillName() %> - <%= skill.getSkillLevel() %>
    </li>
    <%
        }
    %>
</ul>

<h3>Languages</h3>
<ul>
    <%
        for (LanguageDTO lang : cv.getLanguages()) {
    %>
    <li><%= lang.getLanguageName() %> - <%= lang.getLevel() %>
    </li>
    <%
        }
    %>
</ul>

<h3>Interests</h3>
<ul>
    <%
        for (InterestDTO i : cv.getInterests()) {
    %>
    <li><%= i.getInterestName() %>
    </li>
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

<jsp:include page="WEB-INF/layout/footer.jsp"/>

</body>
</html>
