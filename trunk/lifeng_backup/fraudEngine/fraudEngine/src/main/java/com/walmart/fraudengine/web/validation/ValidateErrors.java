package com.walmart.fraudengine.web.validation;

import java.util.HashMap;
import java.util.Map;

public class ValidateErrors {
	
	private Map<String, Map<String, String>> errors;
	
	private int size;
	
	public ValidateErrors(){
		errors = new HashMap<String, Map<String, String>>();
		size = 0;
	}
	
	public boolean hasErrors() {
		return this.size() > 0;
	}
	
	public int size() {
		return size;
	}
	
	public void addError(String fieldName, String constraintName, String message) {
		if (errors.containsKey(fieldName)) {
			Map<String, String> fieldErrors = errors.get(fieldName);
			if ( ! fieldErrors.containsKey(constraintName)) {
				fieldErrors.put(constraintName, message);
				size++;
			}
		} else {
			Map<String, String> fieldErrors = new HashMap<String, String>();
			fieldErrors.put(constraintName, message);
			errors.put(fieldName, fieldErrors);
			size++;
		}
	}
	
	public Map<String, String> getFieldErrors(String fieldName) {
		if (errors.containsKey(fieldName)) {
			return errors.get(fieldName);
		} 
		return null;
	}
	
	public boolean hasFieldErrors(String fieldName) {
		if (errors.containsKey(fieldName)) {
			return true;
		} 
		return false;
	}
	
	public Map<String, Map<String, String>> getErrors() {
		return errors;
	}
	
	@Override
	public String toString(){
		if (this.hasErrors()) {
			StringBuffer sb = null;
			for ( Map.Entry<String, Map<String, String>> entry : this.getErrors().entrySet()) {
				if ( sb == null ) {
					sb = new StringBuffer();
				}
				String field = entry.getKey();
				Map<String, String> constrants = entry.getValue();
				if ( constrants != null ) {
					for ( Map.Entry<String, String> pair : constrants.entrySet()) {
						String constraint = pair.getKey();
						String result = pair.getValue();
						sb.append("[");
						sb.append("field : ");
						sb.append(field);
						sb.append("constraint : ");
						sb.append(constraint);
						sb.append("result : ");
						sb.append(result);
						sb.append("]");
					}
				} else {
					sb.append("[");
					sb.append("field : ");
					sb.append(field);
					sb.append("]");
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}
}
