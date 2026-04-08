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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CV View</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/cv.css">
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/layout/header.jsp"/>
</header>

<main>
    <div class="container">

        <div class="mt-2 mb-3">
            <a href="applications.jsp" class="text-decoration-none small text-secondary">&larr; Back to Applications</a>
        </div>

        <% if (message != null) { %>
        <div class="alert alert-success"><%= message %></div>
        <% } %>

        <% if (cv != null) { %>

        <div class="text-center mb-4">
            <% if (cv.getPhotoUrl() != null) { %>
            <img src="cv.jsp?action=photo&studentId=<%= studentId %>" class="cv-photo shadow-sm" alt="Student photo"/>
            <% } %>

            <h2 class="page-title"><%= cv.getFirstName() %> <%= cv.getLastName() %></h2>
            <p class="text-muted small"><%= cv.getEmail() %></p>
        </div>

        <h5 class="section-title">Summary</h5>
        <div class="cv-item">
            <p class="mb-0 text-dark"><%= cv.getSummary() %></p>
        </div>

        <h5 class="section-title">Education</h5>
        <ul class="cv-list">
            <% for (EducationDTO edu : cv.getEducations()) { %>
            <li class="cv-item">
                <div class="fw-bold"><%= edu.getInstitution() %></div>
                <div class="small text-primary"><%= edu.getDegree() %> (<%= edu.getFieldOfStudy() %>)</div>
                <div class="small text-muted"><%= edu.getStartYear() %> - <%= edu.getEndYear() %></div>
            </li>
            <% } %>
        </ul>

        <h5 class="section-title">Experience</h5>
        <ul class="cv-list">
            <% for (ExperienceDTO exp : cv.getExperiences()) { %>
            <li class="cv-item">
                <div class="fw-bold text-dark"><%= exp.getCompanyName() %></div>
                <div class="text-primary small fw-semibold"><%= exp.getPosition() %></div>
                <div class="small text-muted mb-2"><%= exp.getStartDate() %> - <%= exp.getEndDate() %></div>
                <p class="small mb-0 text-muted"><%= exp.getDescription() %></p>
            </li>
            <% } %>
        </ul>

        <h5 class="section-title">Skills</h5>
        <div class="mb-3 px-2">
            <% for (SkillDTO skill : cv.getSkills()) { %>
            <span class="skill-badge border">
                <span class="fw-bold"><%= skill.getSkillName() %></span>
                <span class="text-muted">| <%= skill.getSkillLevel() %></span>
            </span>
            <% } %>
        </div>

        <h5 class="section-title">Languages</h5>
        <div class="mb-3 px-2">
            <% for (LanguageDTO lang : cv.getLanguages()) { %>
            <span class="skill-badge border">
                <span class="fw-bold"><%= lang.getLanguageName() %></span>
                <span class="text-muted">| <%= lang.getLevel() %></span>
            </span>
            <% } %>
        </div>

        <h5 class="section-title">Interests</h5>
        <div class="mb-3 px-2">
            <% for (InterestDTO i : cv.getInterests()) { %>
            <span class="skill-badge border">
                <span class="fw-bold"><%= i.getInterestName() %></span>
            </span>
            <% } %>
        </div>

        <div class="d-grid gap-2 pt-3">
            <a href="cv.jsp?action=download&studentId=<%= studentId %>" class="btn btn-primary">Download PDF CV</a>
        </div>

        <% } %>
    </div>
</main>

<footer>
    <jsp:include page="/WEB-INF/layout/footer.jsp"/>
</footer>

</body>
</html>
