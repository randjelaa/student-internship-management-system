<%@ page import="com.example.model.dto.StudentResponseDTO" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/29/2026
  Time: 9:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Students</title>
</head>
<body>
<jsp:include page="layout/header.jsp"/>

<h2>Studenti</h2>

<!-- ➕ forma -->
<h3>Dodaj studenta</h3>

<form method="post" action="students">
    <input type="hidden" name="action" value="create"/>

    Email: <input type="text" name="email"/><br/>
    Password: <input type="text" name="password"/><br/>

    Ime: <input type="text" name="firstName"/><br/>
    Prezime: <input type="text" name="lastName"/><br/>

    Broj indeksa: <input type="text" name="indexNumber"/><br/>
    Fakultet: <input type="text" name="faculty"/><br/>
    Godina: <input type="number" name="yearOfStudy"/><br/>

    <button type="submit">Dodaj</button>
</form>

<br/>

<!-- 📊 tabela -->
<table border="1">
    <tr>
        <th>Ime</th>
        <th>Prezime</th>
        <th>Indeks</th>
        <th>Email</th>
        <th>Fakultet</th>
        <th>Godina studija</th>
        <th>Akcije</th>
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

            <!-- DELETE -->
            <form method="post" action="students" style="display:inline;">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" value="<%= s.getId() %>"/>

                <button type="submit">Obriši</button>
            </form>

            <!-- UPDATE -->
            <a href="students?action=edit&id=<%= s.getId() %>">
                <button type="button">Uredi</button>
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
