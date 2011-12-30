package com.walmart.fraudengine.web.form;

import com.walmart.fraudengine.web.validation.Validatable;
import com.walmart.fraudengine.web.validation.ValidateErrors;

public class PosNegFieldForm implements Validatable{

    private Long fieldTypePk;

	private String name;
	
	private String description;

	public Long getFieldTypePk() {
		return fieldTypePk;
	}

	public void setFieldTypePk(Long fieldTypePk) {
		this.fieldTypePk = fieldTypePk;
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

	@Override
	public ValidateErrors validate() {
		// TODO Auto-generated method stub
		return null;
	}
  
}
