<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.StudentResponseDTO" %>

<%
    StudentResponseDTO s = (StudentResponseDTO) request.getAttribute("student");
    String error = (String) request.getAttribute("error");
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit student</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cards.css">

    <script>
        function validateUpdateStudent() {
            let email = document.getElementsByName("email")[0].value.trim();
            let firstName = document.getElementsByName("firstName")[0].value.trim();
            let lastName = document.getElementsByName("lastName")[0].value.trim();
            let year = document.getElementsByName("yearOfStudy")[0].value;

            if (!email || !email.includes("@")) {
                alert("Invalid email.");
                return false;
            }
            if (!firstName || !lastName) {
                alert("First name and last name are required.");
                return false;
            }

            let y = parseInt(year);
            if (!year || y < 1 || y > 6) {
                alert("Year of study must be between 1 and 6.");
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

        <div class="mt-2 mb-3">
            <a href="students" class="text-decoration-none small text-secondary">
                &larr; Back to students
            </a>
        </div>

        <h2 class="page-title">Edit student</h2>

        <% if (error != null) { %>
        <div class="alert alert-danger py-2"><%= error %></div>
        <% } %>

        <div class="grading-card p-3">

            <form method="post" action="students" onsubmit="return validateUpdateStudent()">
                <input type="hidden" name="action" value="update"/>
                <input type="hidden" name="id" value="<%= s.getId() %>"/>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Email</label>
                    <input type="email" name="email" class="form-control"
                           value="<%= s.getEmail() %>"
                           placeholder="student@example.com" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Password</label>
                    <input type="password" name="password" class="form-control"
                           placeholder="Leave blank to keep current password"/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">First name</label>
                    <input type="text" name="firstName" class="form-control"
                           value="<%= s.getFirstName() %>"
                           placeholder="John" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Last name</label>
                    <input type="text" name="lastName" class="form-control"
                           value="<%= s.getLastName() %>"
                           placeholder="Doe" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Index number</label>
                    <input type="text" name="indexNumber" class="form-control"
                           value="<%= s.getIndexNumber() %>"
                           placeholder="IB123/2022" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Faculty</label>
                    <input type="text" name="faculty" class="form-control"
                           value="<%= s.getFaculty() %>"
                           placeholder="Faculty of Electrical Engineering" required/>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Year of study</label>
                    <input type="number" name="yearOfStudy" class="form-control"
                           value="<%= s.getYearOfStudy() %>"
                           min="1" max="6" required/>
                </div>

                <div class="d-grid mt-3">
                    <button type="submit" class="btn btn-primary">
                        Save changes
                    </button>
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