<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.StudentResponseDTO" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Students</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cards.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tables.css">

    <script>
        function validateStudent() {
            let email = document.getElementsByName("email")[0].value;
            let password = document.getElementsByName("password")[0].value;
            let firstName = document.getElementsByName("firstName")[0].value;
            let lastName = document.getElementsByName("lastName")[0].value;
            let index = document.getElementsByName("indexNumber")[0].value;
            let faculty = document.getElementsByName("faculty")[0].value;
            let year = document.getElementsByName("yearOfStudy")[0].value;

            if (!email || !email.includes("@")) {
                alert("Invalid email");
                return false;
            }
            if (!password || password.length < 6) {
                alert("Password must be at least 6 characters");
                return false;
            }
            if (!firstName || !lastName) {
                alert("First and last name are required");
                return false;
            }
            if (!index) {
                alert("Index number is required");
                return false;
            }
            if (!faculty) {
                alert("Faculty is required");
                return false;
            }

            let y = parseInt(year);
            if (!year || y < 1 || y > 6) {
                alert("Year of study must be between 1 and 6");
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
        <h2 class="page-title">Students</h2>

        <table class="table table-hover align-middle">
            <thead class="table-light">
            <tr>
                <th>Name</th>
                <th>Index</th>
                <th>Email</th>
                <th>Faculty</th>
                <th>Year</th>
                <th>Actions</th>
            </tr>
            </thead>

            <tbody>
            <%
                StudentResponseDTO[] students =
                        (StudentResponseDTO[]) request.getAttribute("students");

                if (students != null) {
                    for (StudentResponseDTO s : students) {
            %>

            <tr>
                <td data-label="Name">
                    <%= s.getFirstName() %> <%= s.getLastName() %>
                </td>
                <td data-label="Index"><%= s.getIndexNumber() %></td>
                <td data-label="Email" class="text-break"><%= s.getEmail() %></td>
                <td data-label="Faculty"><%= s.getFaculty() %></td>
                <td data-label="Year"><%= s.getYearOfStudy() %></td>

                <td data-label="Actions">
                    <div class="btn-group-mobile">
                        <a href="students?action=edit&id=<%= s.getId() %>">
                            <button type="button" class="btn btn-sm btn-outline-secondary">
                                Edit
                            </button>
                        </a>

                        <form method="post" action="students" class="d-inline">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="id" value="<%= s.getId() %>"/>
                            <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('Are you sure?')">
                                Delete
                            </button>
                        </form>
                    </div>
                </td>
            </tr>

            <%
                    }
                }
            %>
            </tbody>
        </table>

        <h5 class="section-title text-primary mt-3">Add Student</h5>
        <div class="grading-card p-3">

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <div class="alert alert-danger"><%= error %></div>
            <% } %>

            <form method="post" action="students" onsubmit="return validateStudent()">
                <input type="hidden" name="action" value="create"/>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Email</label>
                    <input type="email" name="email" class="form-control"
                           placeholder="student@example.com" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Password</label>
                    <input type="password" name="password" class="form-control"
                           placeholder="Min 6 characters" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">First name</label>
                    <input type="text" name="firstName" class="form-control" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Last name</label>
                    <input type="text" name="lastName" class="form-control" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Index number</label>
                    <input type="text" name="indexNumber" class="form-control"
                           placeholder="XXXX/YY" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Faculty</label>
                    <input type="text" name="faculty" class="form-control"
                           placeholder="e.g. Faculty of Electrical Engineering" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Year of study</label>
                    <input type="number" name="yearOfStudy" class="form-control"
                           placeholder="1 - 6" min="1" max="6" required/>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-primary">Add Student</button>
                </div>
            </form>
        </div>

        <h5 class="section-title text-primary mt-3">Upload CSV</h5>
        <div class="grading-card p-3">
            <form method="post" action="students" enctype="multipart/form-data">
                <input type="hidden" name="action" value="upload"/>

                <div class="mb-3">
                    <input type="file" name="file" class="form-control"/>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-outline-primary">Upload</button>
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