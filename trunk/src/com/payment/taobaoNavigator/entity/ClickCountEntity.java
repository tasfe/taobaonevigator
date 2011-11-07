package com.payment.taobaoNavigator.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="click_count")
public class ClickCountEntity extends BaseEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToOne
	@JoinColumn(name="product_id")
	private ProductEntity product;

	@Column(name = "count", nullable = false)
	private Integer count;

	@Column(name = "first_click_date")
	private Date firstClickDate;

	@Column(name = "last_click_date")
	private Date lastClickDate;

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
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

	public Integer getId() {
		return id;
	}
}
