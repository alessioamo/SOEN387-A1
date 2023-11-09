package database_package_config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class databaseConfig {
	private String host;
    private int port;
    private String user;
    private String password;
    private String database;

    public databaseConfig() {
        Properties properties = new Properties();
        
        try (FileInputStream fis = new FileInputStream("database-config.properties")) {
            properties.load(fis);
            host = properties.getProperty("db.host");
            port = Integer.parseInt(properties.getProperty("db.port"));
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            database = properties.getProperty("db.database");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }
}
