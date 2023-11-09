package database_package_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database_package_model.Order;

public class OrderDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;

	public OrderDao(Connection con) {
		this.con = con;
	}
	
	public void newOrder(Order order) {
		try { 
			query = "INSERT INTO orders (userId, datePlaced, totalCost) VALUES (?, ?, ?)";
			pst = this.con.prepareStatement(query);
			//pst.setInt(1, order.getOrderId());
			pst.setInt(1, order.getUserId());
			pst.setString(2, order.getDatePlaced());
			pst.setDouble(3, order.getTotalCost());
			pst.executeUpdate();
			query = "SELECT MAX(orderId) FROM orders";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();
			if (rs.next()) {
				order.setOrderId(rs.getInt(1));
			}
			pst.close();
			updateProductsInCart(order);
			updateShippingAddress(order);
			if (order.getTrackingNumber()!=0) {
				updateTrackingNumber(order);
			}
			
		}catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	private void updateShippingAddress(Order order) {
		try {
			query = "UPDATE orders SET shippingAddress = ? WHERE orderId=?;";
			pst = this.con.prepareStatement(query);
			pst.setString(1, order.getShippingAddress());
			pst.setInt(2, order.getOrderId());
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private void updateTrackingNumber(Order order) {
		try {
			query = "UPDATE orders SET trackingNumber = ? WHERE orderId=?;";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, order.getTrackingNumber());
			pst.setInt(2, order.getOrderId());
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private void updateProductsInCart(Order order) {
		try {
			query = "UPDATE orders SET productsInCart = ? WHERE orderId=?;";
			pst = this.con.prepareStatement(query);
			pst.setString(1, order.getProductsInCart());
			pst.setInt(2, order.getOrderId());
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
