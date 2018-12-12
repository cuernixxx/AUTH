package com.isb.sgs.cypher.bean;

import java.util.List;

public class Platform {

	private String platform;
	private List<Product> products;
	
	public Platform(String platform) {
		super();
		this.platform = platform;
	}
	
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		Platform aux = (Platform) obj;
		boolean result = (aux.getPlatform().equals(platform))?true: false;
		return result;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName() + ": {\n");
		sb.append("    platform: ").append(toIndentedString(platform)).append("\n");
		sb.append("    products: ").append(toIndentedString(products)).append("\n");
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

	
	
}
