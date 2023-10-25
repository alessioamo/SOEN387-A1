package database_package_model;

import java.util.ArrayList;

public class Cart{
	//private int quantity;
	
	private ArrayList<Product> cartProducts;
	
	public Cart() {
		cartProducts = new ArrayList<Product>(); 
	}
	
	public boolean findInCart(int id) {
		for (Product p: cartProducts) {
			if (p.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Product> getCartProducts(){
		return cartProducts;
	}
	
	public void getCartProducts(ArrayList<Product> newCart){
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
