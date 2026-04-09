<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.StudentResponseDTO" %>

<html>
<head>
    <title>Students</title>

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
<jsp:include page="layout/header.jsp"/>

<h2>Students</h2>

<h3>Add student</h3>

<form method="post" action="students" onsubmit="return validateStudent()">
    <input type="hidden" name="action" value="create"/>

    <label>
        Email:
        <input type="text" name="email"/>
    </label>
    <br/>
    <label>
        Password:
        <input type="password" name="password"/>
    </label>
    <br/>
    <label>
        First name:
        <input type="text" name="firstName"/>
    </label>
    <br/>
    <label>
        Last name:
        <input type="text" name="lastName"/>
    </label>
    <br/>

    <label>
        Index number:
        <input type="text" name="indexNumber"/>
    </label>
    <br/>
    <label>
        Faculty:
        <input type="text" name="faculty"/>
    </label>
    <br/>
    <label>
        Year of study:
        <input type="number" name="yearOfStudy"/>
    </label>
    <br/>

    <button type="submit">Add</button>
</form>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<div style="color:red;"><%= error %></div>
<%
    }
%>

<br/>

<table border="1">
    <tr>
        <th>First name</th>
        <th>Last name</th>
        <th>Index number</th>
        <th>Email</th>
        <th>Faculty</th>
        <th>Year of study</th>
        <th>Actions</th>
    </tr>

    <%
        StudentResponseDTO[] students =
                (StudentResponseDTO[]) request.getAttribute("students");

        if (students != null) {
            for (StudentResponseDTO s : students) {
    %>

    <tr>
        <td><%= s.getFirstName() %></td>
        <td><%= s.getLastName() %></td>
        <td><%= s.getIndexNumber() %></td>
        <td><%= s.getEmail() %></td>
        <td><%= s.getFaculty() %></td>
        <td><%= s.getYearOfStudy() %></td>
        <td>
            <form method="post" action="students" style="display:inline;">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" value="<%= s.getId() %>"/>
                <button type="submit">Delete</button>
            </form>

            <a href="students?action=edit&id=<%= s.getId() %>">
                <button type="button">Edit</button>
            </a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

<h3>Upload CSV</h3>

<form method="post" action="students" enctype="multipart/form-data">
    <input type="hidden" name="action" value="upload"/>
    <input type="file" name="file"/>
    <button type="submit">Upload</button>
</form>


<jsp:include page="layout/footer.jsp"/>
</body>
</html>
