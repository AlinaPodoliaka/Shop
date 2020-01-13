<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="items" scope="request" type="java.util.List<internetshop.model.Item>"/>
<jsp:useBean id="order_id" scope="request" type="java.lang.Long"/>
<html>
<head>
    <title>Your Current Order</title>
</head>
<br>
<table border="1">
    <tr>
        <td>Order # ${order_id}</td>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.id}"/>
            </td>
            <td>
                <c:out value="${item.name}"/>
            </td>
            <td>
                <c:out value="${item.price}"/>
        </tr>
    </c:forEach>
</table>
</body>
</html>
