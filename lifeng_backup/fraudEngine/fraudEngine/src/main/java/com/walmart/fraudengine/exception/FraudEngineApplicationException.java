package com.walmart.fraudengine.exception;

import java.text.MessageFormat;

/**
 * This class <code>FraudEngineApplicationException</code> is a subclass of <code>Exception</code> and must be thrown
 * when there is an issue related to business logic that the application might want to catch. Throwing this exception
 * will not result in the transaction being marked for rollback by Spring's transaction manager.
 * <p>
 * <strong>Note: </strong>All constructor classes enforce the usage of errorCode passing.
 */
public class FraudEngineApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	private ErrorCode errorCode;
	String details;

	/**
	 * Constructs a new exception with the specified error code. Use this constructor only if the message associated
	 * with the error code does not have any tokens that need to be replaced.
	 * 
	 * @param errorCode Error code to locate the base message.
	 */
	public FraudEngineApplicationException(ErrorCode errorCode) {

		super(errorCode.getMessage());

		this.errorCode = errorCode;
	}

	/**
	 * Constructs a new exception with the specified error code. The exception message is dynamically created and
	 * includes contextual information collected at the time of raising the exception.
	 * 
	 * @param errorCode Error code to locate the base message.
	 * @param tokens An array of strings containing values to be dynamically populated in the base error message.
	 */
	public FraudEngineApplicationException(ErrorCode errorCode, String[] tokens) {

		super(getErrorMessage(errorCode, tokens));

		this.errorCode = errorCode;
	}

	/**
	 * Constructs a new exception with the specified error code and details. Use this constructor only if the message
	 * associated with the error code does not have any tokens that need to be replaced.
	 * 
	 * @param errorCode Error code to locate the base message.
	 */
	public FraudEngineApplicationException(ErrorCode errorCode, String details) {

		super(errorCode.getMessage());

		this.errorCode = errorCode;
		this.details = details;
	}

	/**
	 * Constructs a new exception with the specified error code and cause.
	 * 
	 * @param errorCode Error code to locate the base message.
	 * @param cause A java.lang.Throwable that needs to be linked to the current exception.
	 */
	public FraudEngineApplicationException(ErrorCode errorCode, Throwable cause) {

		super(errorCode.getMessage(), cause);

		this.errorCode = errorCode;
	}

	/**
	 * Constructs a new exception with the specified error code, details, and cause.
	 * 
	 * @param errorCode Error code to locate the base message.
	 * @param cause A java.lang.Throwable that needs to be linked to the current exception.
	 */
	public FraudEngineApplicationException(ErrorCode errorCode, String details, Throwable cause) {

		super(errorCode.getMessage(), cause);

		this.errorCode = errorCode;
		this.details = details;
	}

	/**
	 * Constructs a new exception with the specified error code and cause. The exception message is dynamically created
	 * and includes contextual information collected at the time of raising the exception.
	 * 
	 * @param errorCode Error code to locate the base message.
	 * @param tokens An array of strings containing values to be dynamically populated in the base error message.
	 * @param cause A java.lang.Throwable that needs to be linked to the current exception.
	 */
	public FraudEngineApplicationException(ErrorCode errorCode, String[] tokens, Throwable cause) {

		super(getErrorMessage(errorCode, tokens), cause);

		this.errorCode = errorCode;
	}

	/**
	 * Returns the error code for the exception.
	 * 
	 * @return error code
	 */
	public int getErrorCodeEnum() {

		return this.errorCode.getErrorCode();
	}

	/**
	 * Construct the exception message dynamically to include contextual information collected at the time of raising
	 * the exception.
	 * 
	 * @param errorCode Error code to locate the base message.
	 * @param tokens An array of strings containing values to be dynamically populated in the base error message.
	 * @return Dynamically created error message.
	 */
	private static String getErrorMessage(ErrorCode errorCode, Object[] tokens) {

		return MessageFormat.format(errorCode.getMessage(), tokens);
	}
}