<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login</title>

    <script>
        function validateLogin() {
            let email = document.getElementsByName("email")[0].value;
            let password = document.getElementsByName("password")[0].value;

            if (!email || !email.includes("@")) {
                alert("Invalid email.");
                return false;
            }

            if (!password || password.length < 6) {
                alert("Password is required and has to have minimum 6 characters.");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
<form method="post" action="login" onsubmit="return validateLogin()">
    <label>
        Email:
        <input type="text" name="email"/>
    </label>
    <br/>
    <label>
        Password:
        <input type="password" name="password"/><br/>
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
