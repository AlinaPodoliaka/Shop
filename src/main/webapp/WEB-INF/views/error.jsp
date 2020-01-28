<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="msg" scope="request" type="java.lang.String"/>

<html>
<head>
    <title>Error page</title>
</head>
<body>
<h1>Sorry, something go wrong </h1>
<h2>${msg}</h2><br>
</body>
</html>
