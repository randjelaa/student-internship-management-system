<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 4/4/2026
  Time: 6:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.example.util.ApiClient" %>
<%@ page import="com.example.dto.*" %>

<%
    String studentId = request.getParameter("studentId");
    CvResponseDTO cv = null;

    try {
        cv = ApiClient.get("/cv/student/" + studentId, CvResponseDTO.class, request);
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

<p><b>Summary:</b> <%= cv.getSummary() %></p>

<%
    if (cv.getPhotoUrl() != null) {
%>
<img src="cv-photo.jsp?studentId=<%= studentId %>" width="150"/><%
    }
%>

<h3>Education</h3>
<ul>
    <%
        for (EducationDTO edu : cv.getEducations()) {
    %>
    <li>
        <b><%= edu.getInstitution() %></b> -
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

<a href="cv-download.jsp?studentId=<%= studentId %>">
    Download CV
</a>

<%
    }
%>

<jsp:include page="WEB-INF/layout/footer.jsp"/>

</body>
</html>
