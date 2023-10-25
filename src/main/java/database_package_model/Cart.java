package database_package_model;

import java.util.ArrayList;

public class Cart{
	//private int quantity;
	
	private ArrayList<Product> cartProducts;
	
	public Cart() {
		cartProducts = new ArrayList<Product>(); 
	}
	
	public boolean findInCart(String sku) {
		for (Product p: cartProducts) {
			if (p.getSku().equals(sku)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Product> getCartProducts(){
		return cartProducts;
	}
	
	public void setCartProducts(ArrayList<Product> newCart){
		this.cartProducts = newCart;
	}
	
	public String toString() {
		String cart = "";
		for (Product p:cartProducts) {
			cart = cart + p.toString();
		}
		return cart;
	}
}
