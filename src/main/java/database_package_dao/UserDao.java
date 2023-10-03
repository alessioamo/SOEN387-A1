package database_package_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
