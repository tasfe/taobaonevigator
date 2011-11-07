package com.payment.taobaoNavigator.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Repository;

@Repository
public class ClickCountEntity extends BaseEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "product_id", nullable = false)
	private Integer productId;

	@Column(name = "count", nullable = false)
	private Integer count;

	@Column(name = "first_click_date")
	private Date firstClickDate;

	@Column(name = "last_click_date")
	private Date lastClickDate;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public Integer getId() {
		return id;
	}
}
