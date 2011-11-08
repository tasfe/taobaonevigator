package com.payment.taobaoNavigator.form;

public class PromotionForm {
	
	private Integer id;
	
	private String name;
	
	private String description;
	
	private String picture;
	
	private ProductForm product;
	
	private CatagoryForm catagory;
	
	private boolean enabled;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public ProductForm getProduct() {
		return product;
	}

	public void setProduct(ProductForm product) {
		this.product = product;
	}

	public CatagoryForm getCatagory() {
		return catagory;
	}

	public void setCatagory(CatagoryForm catagory) {
		this.catagory = catagory;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
