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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%= (internship != null) ? "Edit internship" : "Create internship" %>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/cards.css">

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

<header>
    <jsp:include page="/WEB-INF/layout/header.jsp"/>
</header>

<main>
    <div class="container">
        <div class="mt-2 mb-3">
            <a href="internships.jsp" class="text-decoration-none small text-secondary">&larr; Back to internships</a>
        </div>

        <h2 class="page-title"><%= (internship != null) ? "Edit internship" : "Create internship" %></h2>

        <% if (message != null) { %>
        <div class="alert alert-success py-2"><%= message %></div>
        <% } %>

        <div class="grading-card p-3 mb-4">
            <form method="post" onsubmit="return validateInternshipForm()">
                <input type="hidden" name="action" value="create-updateInternship"/>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Title</label>
                    <input type="text" name="title" class="form-control"
                           value="<%= internship != null ? internship.getTitle() : "" %>"
                           placeholder="e.g. Backend developer"/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Description</label>
                    <textarea name="description" class="form-control"
                              rows="4"><%= internship != null ? internship.getDescription() : "" %></textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Location</label>
                    <input type="text" name="location" class="form-control"
                           value="<%= internship != null ? internship.getLocation() : "" %>"
                           placeholder="e.g. Banja Luka / Remote"/>
                </div>

                <div class="row">
                    <div class="col-6 mb-3">
                        <label class="form-label small fw-bold">Start date</label>
                        <input type="date" name="startDate" class="form-control"
                               value="<%= internship != null ? internship.getStartDate() : "" %>"/>
                    </div>

                    <div class="col-6 mb-3">
                        <label class="form-label small fw-bold">End date</label>
                        <input type="date" name="endDate" class="form-control"
                               value="<%= internship != null ? internship.getEndDate() : "" %>"/>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Requirements</label>
                    <textarea name="requirements" class="form-control"
                              rows="3"><%= internship != null ? internship.getRequirements() : "" %></textarea>
                </div>

                <h3 class="form-label small fw-bold">Technologies</h3>

                <div class="row px-2">
                    <% for (TechnologyResponseDTO t : technologies) { %>
                    <div class="col-6 col-md-4 mb-2">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="technologyIds"
                                   id="tech-<%= t.getId() %>" value="<%= t.getId() %>"
                                    <%= selectedTechIds.contains(t.getId()) ? "checked" : "" %> />

                            <label class="form-check-label small" for="tech-<%= t.getId() %>"><%= t.getName() %></label>
                        </div>
                    </div>
                    <% } %>
                </div>

                <div class="d-grid gap-2 mt-4">
                    <button type="submit" class="btn btn-primary">
                        <%= (internship != null) ? "Update internship" : "Create internship" %>
                    </button>
                </div>
            </form>
        </div>

        <h5 class="section-title text-primary">Add new technology</h5>
        <div class="grading-card p-3 mb-4">
            <form method="post" onsubmit="return validateTechForm()">
                <input type="hidden" name="action" value="createTechnology"/>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Technology name</label>
                    <input type="text" name="name" class="form-control" required/>
                </div>

                <div class="d-grid gap-2 mt-4">
                    <button type="submit" class="btn btn-outline-primary">Add</button>
                </div>
            </form>
        </div>

    </div>
</main>

<footer>
    <jsp:include page="/WEB-INF/layout/footer.jsp"/>
</footer>

</body>
</html>