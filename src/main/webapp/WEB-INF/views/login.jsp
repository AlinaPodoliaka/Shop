<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
Login page
<div>${errorMsg}</div>
<form action="${pageContext.request.contextPath}/login" method="post">
    <div class="container">
        <h1>Login</h1>
        <p>Please fill in this form to sing into account.</p>
        <hr>

        <label for="login"><b>Login</b></label>
        <input type="text" placeholder="Enter login" name="login" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" required>

        <hr>

        <button type="submit" class="registerbtn">Login</button>
    </div>

    <div class="container signin">
        <p>Don't have an account? <a href="#">Sign up</a>.</p>
    </div>
</form>
</body>
</html>
