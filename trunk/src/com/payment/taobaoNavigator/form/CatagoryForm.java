package com.payment.taobaoNavigator.form;

public class CatagoryForm {

	private Integer id;

	private String name;
	
	private CatagoryForm parentCatagory;
	
	private String remark;

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

	public CatagoryForm getParentCatagory() {
		return parentCatagory;
	}

	public void setParentCatagory(CatagoryForm parentCatagory) {
		this.parentCatagory = parentCatagory;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
