package database_package_model;

public class Cart extends Product {
	private int quantity;
	
	public Cart() {
		
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String toString() {
		return "Product [id=" + this.getId() + ", name=" + this.getName() + ", price=" + this.getPrice() + ", quantity=" + this.getQuantity() +", category=" + this.getCategory() + ", image="
				+ this.getImage() + ", description=" + this.getDescription() + ", vendor=" + this.getVendor() + ", urlSlug=" + this.getUrlSlug() + ", sku="
				+ this.getSku() + "]";
	}
}
