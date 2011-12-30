package com.walmart.fraudengine.web.form;

import com.walmart.fraudengine.web.validation.Validatable;
import com.walmart.fraudengine.web.validation.ValidateErrors;

public class StatusForm implements Validatable {
    
	private Long orderStatusPk;
	
    private String status;
    
    private String statusDescription;

	public Long getOrderStatusPk() {
		return orderStatusPk;
	}

	public void setOrderStatusPk(Long orderStatusPk) {
		this.orderStatusPk = orderStatusPk;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	@Override
	public ValidateErrors validate() {
		// TODO Auto-generated method stub
		return null;
	}
  
}
