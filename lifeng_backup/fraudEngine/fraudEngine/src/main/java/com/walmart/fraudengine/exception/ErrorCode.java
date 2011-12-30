package com.walmart.fraudengine.exception;

public enum ErrorCode {

	// Database error codes should start from 20001 up to 29999
	HARD_DELETE_NOT_ALLOWED(20002, "Hard deletes in the database are not allowed."),
	
	SIZE_OR_INDEX_NEGATIVE(20008, "StartIndex or size can not be negative value."),
	
	RULE_WITN_NO_PRIORIY(20011, "No rule priority found."),
	RULE_WITH_NO_STATUS(20012, "No rule status found."),
	RULE_INVALID_ID(20013, "Invalid rule id."),
	
	CANNOT_PARSE_RULE_FILE(20016, "Cannot parse the Rule file."),

	// System error codes should start from 30001 up to 39999
	UNCATEGORIZED_SYSTEM_EXCEPTION(30001, "Uncategorized system exception.");

	private int errorCode;
	private String message;

	private ErrorCode(int errorCode, String message) {

		this.errorCode = errorCode;
		this.message = message;
	}

	public int getErrorCode() {

		return errorCode;
	}

	public String getMessage() {

		return message;
	}
}