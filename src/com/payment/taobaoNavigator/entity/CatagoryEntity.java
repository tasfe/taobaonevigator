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
@Table(name="catagory")
public class CatagoryEntity extends BaseEntity {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name")
	private String name;
	
	@OneToOne
	@JoinColumn(name = "parent_catagory")
	private CatagoryEntity parentCatagory;
	
	@Column(name = "remark")
	private String remark;
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CatagoryEntity getParentCatagory() {
		return parentCatagory;
	}

	public void setParentCatagory(CatagoryEntity parentCatagory) {
		this.parentCatagory = parentCatagory;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
