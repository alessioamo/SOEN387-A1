package database_package_dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

import database_package_model.Product;
import database_package_model.User;

public class UserDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public UserDao(Connection con) {
		this.con = con;
	}
	
	public User userLogin(String username, String password) {
		User user = null;
		try {
			query = "select * from users where username=? and password=?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				//for security reasons, don't return user's password
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	public User userAuthentication(String authenticationKey) {
		User user = null;
		
		try {
			boolean matchFound = false;
			
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("users.json");
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            if (rootNode.isArray()) {
            	ArrayNode updatedRootNode = objectMapper.createArrayNode();
            	
                for (JsonNode jsonNode : rootNode) {
                    String key = jsonNode.get("authenticationKey").asText();

                    if (authenticationKey.equals(key)) {
                    	System.out.println("Match found in users.json!");
                    	matchFound = true;
                    	user = new User();
                    	// This if statement is so that we can keep our current code of checking for admin
                    	if (authenticationKey.equals("secret")) {
                    		user.setUsername("admin");
                    	}
                    	int userId = jsonNode.get("id").asInt();
                    	user.setId(userId);
                    	break;
                    }
                    updatedRootNode.add(jsonNode);
                }
                if (!matchFound) {
                	System.out.println("No match found in users.json. Creating new User.");

                    // Create a new user object
                    ObjectNode newUser = objectMapper.createObjectNode();
                    newUser.put("authenticationKey", authenticationKey);

                    // Find the maximum ID in the existing data and increment it
                    JsonNode lastObject = rootNode.get(rootNode.size() - 1);
                    int newId = lastObject.get("id").asInt() + 1;
                    newUser.put("id", newId);

                    // Add the new user object to the updated JSON array
                    updatedRootNode.add(newUser);

                    // Write the updated JSON back to the file
                    objectMapper.writeValue(jsonFile, updatedRootNode);
                    
                    // Login to the new account
                    user = new User();
                    user.setId(newId);
                }
            }
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	public User userLogin(String password) {
		User user = null;
		try {
			query = "select * from users where password=?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, password);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername("");
				user.setAdmin(rs.getBoolean("isAdmin"));
				//for security reasons, don't return user's password
			}
			else {
				System.out.println("User doesn't exist, throwing error.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	public User createAccount(String password) {
		User user = null;
		try {
			// Check if the password already exists for another user
	        query = "SELECT * FROM users WHERE password = ?";
	        pst = this.con.prepareStatement(query);
	        pst.setString(1, password);
	        rs = pst.executeQuery();

	        if (rs.next()) {
	            // Password already exists, return null
	            System.out.println("Password already exists for another user.");
	            return null;
	        }
	        
			query = "INSERT INTO users (username, password, isAdmin) VALUES (?, ?, ?)";
			pst = this.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "");
			pst.setString(2, password);
			pst.setBoolean(3, false);
			pst.executeUpdate();
			
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				int newUserId = rs.getInt(1);
		        user = new User();
		        user.setId(newUserId);
		        user.setUsername("");
		        user.setAdmin(false);
			}
			pst.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	public boolean SetPasscode(User user, String password) {
		try {
			// Check if the password already exists for another user
	        query = "SELECT * FROM users WHERE password = ?";
	        pst = this.con.prepareStatement(query);
	        pst.setString(1, password);
	        rs = pst.executeQuery();

	        if (rs.next()) {
	            // Password already exists
	            System.out.println("Password already exists for another user.");
	            return false;
	        }
	        else {
	        	// Password doesn't exist, change it for the user
	        	query = "UPDATE users SET password = ? WHERE id = ?";
	        	pst = this.con.prepareStatement(query);
	        	pst.setString(1, password);
	        	pst.setInt(2, user.getId());
	        	int rowsUpdated = pst.executeUpdate();
	        	
	        	if (rowsUpdated > 0) {
	                System.out.println("Password updated successfully.");
	                return true;
	            } else {
	                System.out.println("Password update failed.");
	                return false;
	            }
	        }
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public void ChangePermission(User user, boolean role) {
		// For this function, 'role' is either true or false. If it is true, it means that the user is an admin so we want to change them to a non-admin
		// If it is false, it means the user is not an admin, and we want to make them an admin
		System.out.println("Before: " + user.isAdmin());
		
		try {
			query = "UPDATE users SET isAdmin = ? WHERE id = ?";
	        pst = this.con.prepareStatement(query);
	        pst.setInt(2, user.getId());
	        
			if (role) {
				pst.setBoolean(1, !role);
				user.setAdmin(false);
			}
			else {
				pst.setBoolean(1, !role);
				user.setAdmin(true);
			}

			int rowsUpdated = pst.executeUpdate();
        	
        	if (rowsUpdated > 0) {
                System.out.println("Permissions changed successfully.");
            } else {
                System.out.println("Permissions change failed.");
            }
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("After: " + user.isAdmin());
	}
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();

		try {
			query = "select * from users";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				User row = new User();
				row.setId(rs.getInt("id"));
				row.setUsername(rs.getString("username"));
				row.setPassword(rs.getString("password"));
				row.setAdmin(rs.getBoolean("isAdmin"));

				users.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}
	
	public User getUserFromId(String id) {
		User user = new User();
		
		try {
			query = "select * from users WHERE id = ?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, Integer.parseInt(id));
			rs = pst.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setAdmin(rs.getBoolean("isAdmin"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
}
