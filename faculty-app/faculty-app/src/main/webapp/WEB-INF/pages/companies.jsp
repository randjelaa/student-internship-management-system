<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.CompanySummaryDTO" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Companies</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cards.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tables.css">

    <script>
        function validateForm() {
            let email = document.getElementsByName("email")[0].value;
            let password = document.getElementsByName("password")[0].value;
            let name = document.getElementsByName("name")[0].value;
            let website = document.getElementsByName("website")[0].value;

            if (!email || !email.includes("@")) {
                alert("Invalid email");
                return false;
            }
            if (!password || password.length < 6) {
                alert("Password must be at least 6 characters");
                return false;
            }
            if (!name || name.trim() === "") {
                alert("Name is required");
                return false;
            }
            if (website && !website.startsWith("http")) {
                alert("Website must start with http");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>

<header>
    <jsp:include page="layout/header.jsp"/>
</header>

<main>
    <div class="container">

        <h2 class="page-title">Companies</h2>

        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    CompanySummaryDTO[] companies = (CompanySummaryDTO[]) request.getAttribute("companies");
                    if (companies != null) {
                        for (CompanySummaryDTO c : companies) {
                %>
                <tr style="cursor:pointer;" onclick="window.location='companies?id=<%= c.getId() %>'">
                    <td data-label="Name"><%= c.getName() %></td>
                    <td data-label="Status"><%= c.isActive() ? "Active" : "Inactive" %></td>
                    <td data-label="Actions">
                        <form method="post" action="companies" class="d-inline">
                            <input type="hidden" name="id" value="<%= c.getId() %>"/>
                            <% if (c.isActive()) { %>
                            <input type="hidden" name="action" value="deactivate"/>
                            <button type="submit" class="btn btn-sm btn-warning">Deactivate</button>
                            <% } else { %>
                            <input type="hidden" name="action" value="activate"/>
                            <button type="submit" class="btn btn-sm btn-success">Activate</button>
                            <% } %>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
        </div>

        <h5 class="section-title text-primary mt-2">Add Company</h5>
        <div class="grading-card p-3">

            <% String error = (String) request.getAttribute("error");
                if (error != null) { %>
            <div class="alert alert-danger"><%= error %></div>
            <% } %>

            <form method="post" action="companies" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="create"/>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Email</label>
                    <input type="email" name="email" class="form-control" placeholder="company@example.com" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Password</label>
                    <input type="password" name="password" class="form-control" placeholder="••••••••" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Name</label>
                    <input type="text" name="name" class="form-control" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Description</label>
                    <input type="text" name="description" class="form-control"/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Website</label>
                    <input type="url" name="website" class="form-control" placeholder="https://example.com"/>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-primary">Add Company</button>
                </div>
            </form>
        </div>
    </div>
</main>

<footer>
    <jsp:include page="layout/footer.jsp"/>
</footer>

</body>
</html>
