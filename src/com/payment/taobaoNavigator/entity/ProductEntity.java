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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="product")
public class ProductEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	public static final String FETCH_ALL = "FETCH_ALL";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToOne
	@JoinColumn(name="catagory_id")
	private CatagoryEntity catagory;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price", nullable = false)
	private Double price;

	@Column(name = "picture")
	private String picture;

	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@Column(name = "modified_date")
	private Date modifiedDate;

	public CatagoryEntity getCatagory() {
		return catagory;
	}

	public void setCatagory(CatagoryEntity catagory) {
		this.catagory = catagory;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getId() {
		return id;
	}
}
