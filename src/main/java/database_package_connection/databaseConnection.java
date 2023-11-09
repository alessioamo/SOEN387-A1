package database_package_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import database_package_config.databaseConfig;

public class databaseConnection {
	/*
	private static Connection connection = null;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soen387_assignment","root","Admin123.");
			System.out.println("Connected.");
		}
		return connection;
	}
	*/
	
	private static final databaseConfig config = new databaseConfig();

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://" + config.getHost() + ":" + config.getPort() + "/" + config.getDatabase(),
                config.getUser(),
                config.getPassword()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
