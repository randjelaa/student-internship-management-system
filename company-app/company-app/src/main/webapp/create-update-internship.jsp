<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.dto.*" %>
<%@ page import="com.example.service.*" %>
<%@ page import="com.example.util.AuthUtil" %>

<%
    LoginResponse user = AuthUtil.requireUser(request, response);
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

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

        String action = request.getParameter("action");

        try {
            if ("createTechnology".equals(action)) {
                String name = request.getParameter("name");

                if (name != null && !name.isEmpty()) {
                    techService.create(name, request);
                }

                response.sendRedirect("create-update-internship.jsp" + (internshipId != null ? "?id=" + internshipId : ""));
                return;
            }

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

    Set<Long> selectedTechIds = new HashSet<>();
    if (internship != null && internship.getTechnologies() != null) {
        for (TechnologyResponseDTO t : technologies) {
            if (internship.getTechnologies().contains(t.getName())) {
                selectedTechIds.add(t.getId());
            }
        }
    }
%>

<html>
<head>
    <title><%= (internship != null) ? "Edit Internship" : "Create Internship" %></title>
</head>
<body>

<jsp:include page="/WEB-INF/layout/header.jsp"/>

<h2><%= (internship != null) ? "Edit Internship" : "Create Internship" %>
</h2>

<form method="post">
    <label>
        Title:
        <input type="text" name="title" value="<%= internship != null ? internship.getTitle() : "" %>"/>
    </label>
    <br/><br/>

    <label>
        Description:
        <textarea name="description"><%= internship != null ? internship.getDescription() : "" %></textarea>
    </label>
    <br/><br/>

    <label>
        Location:
        <input type="text" name="location" value="<%= internship != null ? internship.getLocation() : "" %>"/>
    </label>
    <br/><br/>

    <label>
        Start Date:
        <input type="date" name="startDate" value="<%= internship != null ? internship.getStartDate() : "" %>"/>
    </label>
    <br/><br/>

    <label>
        End Date:
        <input type="date" name="endDate" value="<%= internship != null ? internship.getEndDate() : "" %>"/>
    </label>
    <br/><br/>

    <label>
        Requirements:
        <textarea name="requirements"><%= internship != null ? internship.getRequirements() : "" %></textarea>
    </label>
    <br/><br/>

    <h3>Technologies</h3>
    <%
        for (TechnologyResponseDTO t : technologies) {
    %>
    <label>
        <input type="checkbox" name="technologyIds"
               value="<%= t.getId() %>"<%= selectedTechIds.contains(t.getId()) ? "checked" : "" %> />
        <%= t.getName() %>
    </label><br/>
    <%
        }
    %>

    <br/>

    <button type="submit"><%= (internship != null) ? "Update Internship" : "Create Internship" %>
    </button>
</form>

<hr/>

<h3>Add New Technology</h3>

<form method="post">
    <input type="hidden" name="action" value="createTechnology"/>
    <label>
        Technology name:
        <input type="text" name="name" placeholder="Technology name"/>
    </label>
    <button type="submit">Add</button>
</form>

<% if (message != null) { %>
<p style="color:red;"><%= message %></p>
<% } %>

<jsp:include page="WEB-INF/layout/footer.jsp"/>

</body>
</html>