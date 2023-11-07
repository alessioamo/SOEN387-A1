package database_package_model;
import  java.time.LocalDate;

public class Order {
	
	private int orderId;
	private String shippingAddress;
	private int trackingNumber;
	private String datePlaced;
	private String productsInCart; //TODO not sure what we do with this yet cause json file
	private int userId; //TODO do we import user for this?
	
	public Order() {
	}
	
	public Order(int orderId, String shippingAddress, int trackingNumber, String productsInCart,
			int userId) {
		this.orderId = orderId;
		this.shippingAddress = shippingAddress;
		this.trackingNumber = trackingNumber;
		this.datePlaced = LocalDate.now().toString();
		this.productsInCart = productsInCart;
		this.userId = userId;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public String getShippingAddress() {
		return shippingAddress;
	}
	
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	public int getTrackingNumber() {
		return trackingNumber;
	}
	
	public void setTrackingNumber(int trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	
	public String getProductsInCart() {
		return productsInCart;
	}
	
	public void setProductsInCart(String productsInCart) {
		this.productsInCart = productsInCart;
	}
	
	public String getDatePlaced() {
		return datePlaced;
	}
	
	public void setDatePlaced(String datePlaced) {
		this.datePlaced = datePlaced;
	}
}
