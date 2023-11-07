package database_package_dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

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
}
