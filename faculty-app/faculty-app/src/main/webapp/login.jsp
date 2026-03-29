<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" action="login">
    <label>
        Email:
        <input type="text" name="email" />
    </label>
    <br/>
    <label>
        Password:
        <input type="password" name="password" /><br/>
    </label>
    <button type="submit">Login</button>
</form>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<p style="color:red;"><%= error %></p>
<%
    }
%>

</body>
</html>
