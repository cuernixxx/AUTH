package com.isb.sgs.cypher.bean;

import java.util.List;

public class Product {

	private String product;
	private List<String> versions;

	public Product(String product) {
		super();
		this.product = product;
	}

	@Override
	public boolean equals(Object obj) {
		Product aux = (Product) obj;
		boolean result = (aux.getProduct().equals(product)) ? true : false;
		return result;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public List<String> getVersions() {
		return versions;
	}

	public void setVersions(List<String> versions) {
		this.versions = versions;
	}
}
