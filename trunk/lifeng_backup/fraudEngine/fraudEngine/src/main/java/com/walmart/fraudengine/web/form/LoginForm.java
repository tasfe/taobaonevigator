package com.walmart.fraudengine.web.form;

import com.walmart.fraudengine.web.validation.Validatable;
import com.walmart.fraudengine.web.validation.ValidateErrors;

public class LoginForm implements Validatable{

    private String userName;

    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }

	@Override
	public ValidateErrors validate() {
		// TODO Auto-generated method stub
		return null;
	}
}
