<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.dto.*" %>
<%@ page import="com.example.service.*" %>

<%
    LoginResponse user = (LoginResponse) request.getSession().getAttribute("user");
    if (user == null) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    String message = null;
    TechnologyService techService = new TechnologyService();
    InternshipService internshipService = new InternshipService();

    List<TechnologyResponseDTO> technologies = new ArrayList<>();
    try {
        technologies = techService.getAll(request);
    } catch (Exception e) {
        message = "Error in getting technologies";
        e.printStackTrace();
    }

    String idParam = request.getParameter("id");
    InternshipResponseDTO internship = null;
    Long internshipId = null;

    if (idParam != null) {
        try {
            internshipId = Long.parseLong(idParam);
            internship = internshipService.getById(internshipId, request);
        } catch (Exception e) {
            message = "Error in getting internship";
            e.printStackTrace();
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

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String action = request.getParameter("action");

        try {
            if ("createTechnology".equals(action)) {
                String name = request.getParameter("name");

                if (name == null || name.trim().isEmpty()) {
                    message = "Technology name is required";
                } else {
                    techService.create(name.trim(), request);
                    response.sendRedirect("create-update-internship.jsp" + (internshipId != null ? "?id=" + internshipId : ""));
                    return;
                }
            } else if ("create-updateInternship".equals(action)) {
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String location = request.getParameter("location");
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                String requirements = request.getParameter("requirements");

                if (title == null || title.trim().isEmpty()) {
                    message = "Title is required";
                } else if (description == null || description.trim().isEmpty()) {
                    message = "Description is required";
                } else if (location == null || location.trim().isEmpty()) {
                    message = "Location is required";
                } else if (startDate == null || startDate.isEmpty()) {
                    message = "Start date is required";
                } else if (endDate == null || endDate.isEmpty()) {
                    message = "End date is required";
                }

                if (message == null) {
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
                }
            } else {
                message = "Unknown action";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Error saving internship";
        }
    }
%>

<html>
<head>
    <title><%= (internship != null) ? "Edit Internship" : "Create Internship" %></title>

    <script>
        function validateInternshipForm() {
            const title = document.querySelector('[name="title"]').value;
            const description = document.querySelector('[name="description"]').value;
            const location = document.querySelector('[name="location"]').value;
            const startDate = document.querySelector('[name="startDate"]').value;
            const endDate = document.querySelector('[name="endDate"]').value;

            if (!title.trim()) {
                alert("Title is required");
                return false;
            }
            if (!description.trim()) {
                alert("Description is required");
                return false;
            }
            if (!location.trim()) {
                alert("Location is required");
                return false;
            }
            if (!startDate) {
                alert("Start date is required");
                return false;
            }
            if (!endDate) {
                alert("End date is required");
                return false;
            }

            if (startDate > endDate) {
                alert("Start date cannot be after end date");
                return false;
            }

            return true;
        }

        function validateTechForm() {
            const name = document.querySelector('[name="name"]').value;

            if (!name.trim()) {
                alert("Technology name is required");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>

<jsp:include page="/WEB-INF/layout/header.jsp"/>

<h2><%= (internship != null) ? "Edit Internship" : "Create Internship" %>
</h2>

<%
    if (message != null) {
%>
<p style="color:green;"><%= message %></p>
<%
    }
%>

<form method="post" onsubmit="return validateInternshipForm()">
    <input type="hidden" name="action" value="create-updateInternship"/>
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

<form method="post" onsubmit="return validateTechForm()">
    <input type="hidden" name="action" value="createTechnology"/>
    <label>
        Technology name:
        <input type="text" name="name" placeholder="Technology name"/>
    </label>
    <button type="submit">Add</button>
</form>

<br/><br/>
<a href="internships.jsp">
    <button type="button">Back</button>
</a>

<jsp:include page="WEB-INF/layout/footer.jsp"/>

</body>
</html>