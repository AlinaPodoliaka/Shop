# Shop
##Shop is an example of real internet shop.
An online shop in which the buyer has the opportunity to register, log in, log out, view the list of available items, add the necessary items to the bucket and place an order. Also implemented the ability for the administrator to add items, see a list of registered users.
##Technologies used: 
* Java 
* Maven 
* JSTL 
* Servlets
* Log4j 
* MySQL 
* Tomcat 
##Project Structure
The project has a clear structure. It has models, service and dao layers. 
The project has controllers for management requests from users. Registration, login, and logout have full functionality. Users can add items to the shopping cart, buy items and see a history of orders. Admin can create new items, delete them. Also, the admin can see all users. Because of filters, users cannot see pages for admin, and admin cannot see pages for users.
###How to start for users?
Add new user via Registration form and enjoy your shopping :)
###For developers
* Open downloaded project in your IDE as Maven Project.
* Configure Tomcat.
* Use src\main\resources\init_db.sql file to generate necessary database.
* Change path for your .log file in \src\main\resources\log4j.properties.
* Run the project.
###Author
https://github.com/AlinaPodoliaka


