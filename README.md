# SOEN387-A1
 
Tools used and versions
Tomcat v10.1
jdk-17.0.3.1
Eclipse 2023-09

Instructions:
To generate the .war file, proceed to Eclipse and right click the project. Navigate to Export and then select WAR file. Afterwards, make sure the project you want is selected and choose the destination for 
the WAR file. Then click on finish.

We connected a database with the attached .sql dump file. The host=localhost, username=dbuser, password=dbpass, port=3306, database=soen387 as per the requirements of the assignment. These can be altered 
in the ‘databaseConnection.java’ file in the database_package_connection package.

On the webpage, there are multiple features available. From the homepage, users can click on login to authenticate themselves and gain access to their orders page. Users are stored in users.json with their 
authentication key and user id. If a user enters an existing key, they will login and be able to view their past orders. If a user enters a new key, a new user will be created in users.json and they will 
now be able to view their orders page as well. There are 2 default users in users.json but more can be added. The first has the key ‘secret’ which means that they are an admin. When logged in, you will 
gain admin privileges and be able to edit and create products, view all orders, ship orders, and every other required admin function. The second key is ‘aaa’ and it is a regular user that you can test yourself.
