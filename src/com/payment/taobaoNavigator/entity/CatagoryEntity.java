package com.payment.taobaoNavigator.entity;

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
@Table(name = "catagory")
public class CatagoryEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	public static final String FETCH_ALL = "FETCH_ALL";
	
	public static final String BYPARENTID = "ByParentId";
	

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

	public void setId(Integer id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "id : " + this.getId() + ", name:" + this.getName() + "\n";
	}
}
