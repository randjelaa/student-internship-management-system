<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.StudentResponseDTO" %>

<html>
<head>
    <title>Edit student</title>
</head>
<body>
<jsp:include page="layout/header.jsp"/>

<h2>Edit student</h2>

<%
    StudentResponseDTO s = (StudentResponseDTO) request.getAttribute("student");
%>

<form method="post" action="students">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="<%= s.getId() %>"/>

    <label>
        Email:
        <input type="text" name="email" value="<%= s.getEmail() %>"/>
    </label>
    <br/>
    <label>
        Password:
        <input type="password" name="password"/>
    </label>
    <br/>

    <label>
        First name:
        <input type="text" name="firstName" value="<%= s.getFirstName() %>"/>
    </label>
    <br/>
    <label>
        Last name:
        <input type="text" name="lastName" value="<%= s.getLastName() %>"/>
    </label>
    <br/>

    <label>
        Index number:
        <input type="text" name="indexNumber" value="<%= s.getIndexNumber() %>"/>
    </label>
    <br/>
    <label>
        Faculty:
        <input type="text" name="faculty" value="<%= s.getFaculty() %>"/>
    </label>
    <br/>
    <label>
        Year of study:
        <input type="number" name="yearOfStudy" value="<%= s.getYearOfStudy() %>"/>
    </label>
    <br/>

    <button type="submit">Save</button>
</form>

<br/>

<a href="students">Back</a>

<jsp:include page="layout/footer.jsp"/>
</body>
</html>
