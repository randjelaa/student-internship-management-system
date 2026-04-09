<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.StudentResponseDTO" %>

<html>
<head>
    <title>Students</title>
</head>
<body>
<jsp:include page="layout/header.jsp"/>

<h2>Students</h2>

<h3>Add student</h3>

<form method="post" action="students">
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
