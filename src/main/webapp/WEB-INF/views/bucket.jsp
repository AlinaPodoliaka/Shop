<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bucket" scope="request" type="internetshop.model.Bucket"/>
<html>
<title>BUCKET</title>
</head>
<body>
Items in Your Bucket:
<table border="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Price</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="item" items="${bucket.items}">
        <tr>
            <td>
                <c:out value="${item.id}"/>
            </td>
            <td>
                <c:out value="${item.name}"/>
            </td>
            <td>
                <c:out value="${item.price}"/>
            </td>

            <td>
                <a href="/Internet_Shop_war_exploded/deleteItemFromBucket?item_id=${item.id}">DELETE</a>
            </td>

        </tr>
    </c:forEach>
</table>
<br>
<a href="/Internet_Shop_war_exploded/currentOrder">Complete Order</a>
</br>
</body>
</html>
