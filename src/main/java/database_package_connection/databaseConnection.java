package database_package_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
	private static Connection connection = null;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			try {
				Class.forName("org.sqlite.JDBC");
			}catch (Exception e){
				//TODO add exception or sum shit
				System.out.println("OOPSIES");
			}
			// The first connection is the config for the students
			connection = DriverManager.getConnection("jdbc:sqlite:/Users/boisghur/Documents/GitHub/SOEN387-A1/database.db");
			// The second connection is the config for the professor/TAs
			// connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soen387","dbuser","dbpass.");
			System.out.println("Connected.");
		}
		return connection;
	}
}
