package com.payment.taobaoNavigator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="promotion")
public class PromotionEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	public static final String FETCH_ALL = "FETCH_ALL";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "picture")
	private String picture;

	@Column(name = "enabled", nullable = false)
	private Integer enabled;

	@OneToOne
	@JoinColumn(name="product_id")
	private ProductEntity product;
	
	@OneToOne
	@JoinColumn(name="catagory_id")
	private CatagoryEntity catagory;

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

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public CatagoryEntity getCatagory() {
		return catagory;
	}

	public void setCatagory(CatagoryEntity catagory) {
		this.catagory = catagory;
	}

	public Integer getId() {
		return id;
	}
}
