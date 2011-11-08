package com.payment.taobaoNavigator.form;

import java.sql.Date;

public class ClickCountForm {

	private Integer id;

	private ProductForm product;

	private Integer count;

	private Date firstClickDate;

	private Date lastClickDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProductForm getProduct() {
		return product;
	}

	public void setProduct(ProductForm product) {
		this.product = product;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getFirstClickDate() {
		return firstClickDate;
	}

	public void setFirstClickDate(Date firstClickDate) {
		this.firstClickDate = firstClickDate;
	}

	public Date getLastClickDate() {
		return lastClickDate;
	}

	public void setLastClickDate(Date lastClickDate) {
		this.lastClickDate = lastClickDate;
	}
}
