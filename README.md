### Development Environment:
Tomcat v10.1
jdk-17.0.3.1
Eclipse 2023-09
SQLite Version 3.39.5
### Instructions:
To generate the .war file, proceed to Eclipse and right click the project. Navigate to Export and then select WAR file. Afterwards, make sure the project you want is selected and choose the destination for the WAR file. Then click on finish.

The project database, consisting of 3 tables, can be found in the project repository as “database.db”, along with the SQL dump: “SQLiteDump20231126.sql”. We migrated from MySQL to SQLite for asignment 3. We did this by exporting the MySQL database to a dump file, we then made slight changes to the file to comply with SQLite syntax. We created the .db file from the altered dump, by executing “cat  SQLiteDump20231126.sql | sqlite3 database.db” in the terminal.

On the webpage, there are multiple features available. Users can choose to login or remain logged out. When logged out, users are still able to add items to their cart and place an order, however when they do they are prompted with the order id so they can claim it once they login. On the login page, users can enter a password to login, or enter a new password to create an account. If they enter an incorrect password (too short, user already exists if trying to create, etc) they will be notified and prompted to try again. Once logged in, users can add to their cart, place orders, and view all their orders. They can also view their user information, change their password (there are checks here as well to make sure it doesn’t exist and is a valid size), and claim an order if they enter the id (with appropriate error handling). Admins have all the same functionalities, as well as their previous functions (admin toolkit), and can see all users, all orders, and can change the permissions of other users (make them admins or not) but they can’t change their own permissions because that doesn’t make any sense.

