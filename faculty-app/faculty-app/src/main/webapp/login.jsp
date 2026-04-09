<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">

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
<main class="container">
    <h2 class="page-title text-primary text-center mb-4">Internship System</h2>

    <div class="login-card text-center p-3">
        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
        <div class="alert alert-danger py-2 small"><%= error %></div>
        <% } %>

        <form method="post" action="login" onsubmit="return validateLogin()" class="text-start">
            <div class="mb-3">
                <label class="form-label small fw-bold">Email address</label>
                <input type="email" name="email" class="form-control"
                       placeholder="name@company.com" required/>
            </div>

            <div class="mb-3">
                <label class="form-label small fw-bold">Password</label>
                <input type="password" name="password" class="form-control"
                       placeholder="••••••••" required/>
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-primary">Login</button>
            </div>
        </form>
    </div>
</main>
</body>
</html>
