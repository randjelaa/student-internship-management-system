<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.dto.*" %>
<%@ page import="com.example.service.*" %>
<%@ page import="com.example.util.AuthUtil" %>

<%
    LoginResponse user = AuthUtil.requireUser(request, response);
    if (user == null) return;

    TechnologyService techService = new TechnologyService();
    InternshipService internshipService = new InternshipService();

    List<TechnologyResponseDTO> technologies = null;
    try {
        technologies = techService.getAll(request);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }

    String message = null;

    String idParam = request.getParameter("id");
    InternshipResponseDTO internship = null;
    Long internshipId = null;

    if (idParam != null) {
        try {
            internshipId = Long.parseLong(idParam);
            internship = internshipService.getById(internshipId, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    if ("POST".equalsIgnoreCase(request.getMethod())) {

        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String location = request.getParameter("location");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String requirements = request.getParameter("requirements");

            String[] techIds = request.getParameterValues("technologyIds");

            Set<Long> techSet = new HashSet<>();
            if (techIds != null) {
                for (String id : techIds) {
                    techSet.add(Long.parseLong(id));
                }
            }

            if (internshipId != null) {
                // UPDATE
                UpdateInternshipRequest updateDto = new UpdateInternshipRequest();
                updateDto.setTitle(title);
                updateDto.setDescription(description);
                updateDto.setLocation(location);
                updateDto.setStartDate(startDate);
                updateDto.setEndDate(endDate);
                updateDto.setRequirements(requirements);
                updateDto.setTechnologyIds(techSet);

                internshipService.update(internshipId, updateDto, request);

            } else {
                // CREATE
                CreateInternshipRequest createDto = new CreateInternshipRequest();
                createDto.setTitle(title);
                createDto.setDescription(description);
                createDto.setLocation(location);
                createDto.setStartDate(startDate);
                createDto.setEndDate(endDate);
                createDto.setRequirements(requirements);
                createDto.setTechnologyIds(techSet);

                internshipService.create(createDto, request);
            }

            response.sendRedirect("internships.jsp");
            return;

        } catch (Exception e) {
            e.printStackTrace();
            message = "Error saving internship";
        }
    }

    // pomoćna lista za checked checkboxe
    Set<String> selectedTechNames = new HashSet<>();
    if (internship != null && internship.getTechnologies() != null) {
        selectedTechNames.addAll(internship.getTechnologies());
    }
%>

<html>
<head>
    <title><%= (internship != null) ? "Edit Internship" : "Create Internship" %></title>
</head>
<body>

<jsp:include page="/WEB-INF/layout/header.jsp"/>

<h2><%= (internship != null) ? "Edit Internship" : "Create Internship" %></h2>

<form method="post">

    Title:
    <input type="text" name="title"
           value="<%= internship != null ? internship.getTitle() : "" %>"/>
    <br/><br/>

    Description:
    <textarea name="description"><%= internship != null ? internship.getDescription() : "" %></textarea>
    <br/><br/>

    Location:
    <input type="text" name="location"
           value="<%= internship != null ? internship.getLocation() : "" %>"/>
    <br/><br/>

    Start Date:
    <input type="date" name="startDate"
           value="<%= internship != null ? internship.getStartDate() : "" %>"/>
    <br/><br/>

    End Date:
    <input type="date" name="endDate"
           value="<%= internship != null ? internship.getEndDate() : "" %>"/>
    <br/><br/>

    Requirements:
    <textarea name="requirements"><%= internship != null ? internship.getRequirements() : "" %></textarea>
    <br/><br/>

    <h3>Technologies</h3>

    <%
        for (TechnologyResponseDTO t : technologies) {
    %>
    <label>
        <input type="checkbox" name="technologyIds"
               value="<%= t.getId() %>"
                <%= selectedTechNames.contains(t.getName()) ? "checked" : "" %> />
        <%= t.getName() %>
    </label><br/>
    <%
        }
    %>

    <br/>

    <button type="submit">
        <%= (internship != null) ? "Update Internship" : "Create Internship" %>
    </button>
</form>

<hr/>

<h3>Add New Technology</h3>

<form method="post" action="create-technology.jsp">
    <input type="text" name="name" placeholder="Technology name"/>
    <button type="submit">Add</button>
</form>

<% if (message != null) { %>
<p style="color:red;"><%= message %></p>
<% } %>

</body>
</html>