<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="items" scope="request" type="java.util.List<internetshop.model.Item>"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Items</title>
</head>
<body>
Items:
<table border="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Price</th>
        <th>ADD to Bucket</th>
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
            </td>

            <td>
                <a href="/Internet_Shop_war_exploded/addItemToBucket?item_id=${item.id}">ADD</a>
            </td>


        </tr>
    </c:forEach>
</table>

</body>
</html>
