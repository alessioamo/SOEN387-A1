package tests;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Before;
import org.junit.Test;

import database_package_dao.BusinessFunctions;
import database_package_dao.UserDao;
import database_package_model.Order;
import database_package_model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestClass {
	
	private UserDao udao;
	private BusinessFunctions businessFunctions;
    private JdbcConnectionPool connectionPool;

    @Before
    public void setUp() throws Exception {
        // Set up an H2 in-memory database
        connectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa");
        udao = new UserDao(connectionPool.getConnection());
        businessFunctions = new BusinessFunctions(connectionPool.getConnection());

        // Initialize the database with test data
        initializeTestData();
    }

    private void initializeTestData() {
        try (Connection connection = connectionPool.getConnection()) {
        	// Create the 'USERS' and 'ORDERS' table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255), isAdmin BOOLEAN)";
            try (PreparedStatement createTableStmt = connection.prepareStatement(createTableQuery)) {
                createTableStmt.executeUpdate();
            }
            String createTableSQL = "CREATE TABLE IF NOT EXISTS orders (" +
                    "orderId INT AUTO_INCREMENT PRIMARY KEY, " +
                    "shippingAddress VARCHAR(255), " +
                    "trackingNumber INT, " +
                    "datePlaced DATE, " +
                    "productsInCart VARCHAR(1000), " +
                    "totalCost DOUBLE, " +
                    "userId INT)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
                preparedStatement.executeUpdate();
            }
            
            // Insert sample data into the 'users' table without providing a value for 'ID'
            String insertQuery = "INSERT INTO users (username, password, isAdmin) VALUES (?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, "user1");
                pst.setString(2, "password1");
                pst.setBoolean(3, false);
                
                int rowsAffected = pst.executeUpdate();
                
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = pst.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        System.out.println("Generated ID: " + generatedId);
                    }
                }
            }
            String insertOrderQuery = "INSERT INTO orders (shippingAddress, trackingNumber, datePlaced, productsInCart, totalCost, userId) VALUES ('123 Main St', 0, '2023-01-01', '[{\"id\":1,\"name\":\"Product1\",\"quantity\":2,\"vendor\":\"Vendor1\",\"sku\":\"SKU123\",\"price\":\"19.99\"}]', 39.98, 1)";
            try (PreparedStatement insertOrderStmt = connection.prepareStatement(insertOrderQuery)) {
                insertOrderStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	@Test
    public void testSetPasscodeValidPassword() {
        User user = udao.getUserFromId("1");
        String validPassword = "newValidPassword";
        assertTrue(udao.SetPasscode(user, validPassword));
    }

    @Test
    public void testSetPasscodeShortPassword() {
    	User user = udao.getUserFromId("1");
        String shortPassword = "123";
        assertFalse(udao.SetPasscode(user, shortPassword));
    }

    @Test
    public void testSetPasscodeExistingPassword() {
    	User user = udao.getUserFromId("1");
        String existingPassword = "password1";
        assertFalse(udao.SetPasscode(user, existingPassword));
    }

    @Test
    public void testSetOrderOwner() {
        Order order = businessFunctions.getOrderById("1");
        User user = udao.getUserFromId("1");
        businessFunctions.setOrderOwner(order.getOrderId(), user.getId());
        Order updatedOrder = businessFunctions.getOrderById("1");
        assertEquals(user.getId(), updatedOrder.getUserId());
    }
}
