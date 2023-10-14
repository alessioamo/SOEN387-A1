package database_package_model;

public class Product {
	private int id;
	private String name;
	private String category;
	private Double price;
	private String image;
	private String description;
	private String vendor;
	private String urlSlug;
	private String sku;
	
	public Product() {
	}
	
	public Product(int id, String name, String category, Double price, String image, String description, String vendor, String urlSlug, String sku) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.image = image;
		this.description = description;
		this.vendor = vendor;
		this.urlSlug = urlSlug;
		this.sku = sku;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getUrlSlug() {
		return urlSlug;
	}

	public void setUrlSlug(String urlSlug) {
		this.urlSlug = urlSlug;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", category=" + category + ", price=" + price + ", image="
				+ image + ", description=" + description + ", vendor=" + vendor + ", urlSlug=" + urlSlug + ", sku="
				+ sku + "]";
	}

	

}
