<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add item</title>
</head>
<body>
Create a new Item
<form action="/Internet_Shop_war_exploded/addItem" method="post">
    <div class="container">
        <h1></h1>
        <p>Please fill in this form to create an item.</p>
        <hr>

        <label for="item_name"><b>Name</b></label>
        <input type="text" placeholder="Name" name="item_name" required>

        <label for="item_price"><b>Price</b></label>
        <input type="text" placeholder="price" name="item_price" required>

        <hr>


        <button type="submit" class="registerbtn">Add item</button>
    </div>


</form>
</body>
</html>
