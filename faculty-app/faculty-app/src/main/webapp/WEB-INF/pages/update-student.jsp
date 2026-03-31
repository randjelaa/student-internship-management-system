<%@ page import="com.example.dto.StudentResponseDTO" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/31/2026
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update student</title>
</head>
<body>
<jsp:include page="layout/header.jsp"/>

<h2>Uredi studenta</h2>

<%
    StudentResponseDTO s = (StudentResponseDTO) request.getAttribute("student");
%>

<form method="post" action="students">

    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="<%= s.getId() %>"/>

    Email: <input type="text" name="email" value="<%= s.getEmail() %>"/><br/>
    Password: <input type="text" name="password"/><br/>

    Ime: <input type="text" name="firstName" value="<%= s.getFirstName() %>"/><br/>
    Prezime: <input type="text" name="lastName" value="<%= s.getLastName() %>"/><br/>

    Indeks: <input type="text" name="indexNumber" value="<%= s.getIndexNumber() %>"/><br/>
    Fakultet: <input type="text" name="faculty" value="<%= s.getFaculty() %>"/><br/>
    Godina: <input type="number" name="yearOfStudy" value="<%= s.getYearOfStudy() %>"/><br/>

    <button type="submit">Sačuvaj</button>

</form>

<br/>
<a href="students">Nazad</a>

<jsp:include page="layout/footer.jsp"/>
</body>
</html>
