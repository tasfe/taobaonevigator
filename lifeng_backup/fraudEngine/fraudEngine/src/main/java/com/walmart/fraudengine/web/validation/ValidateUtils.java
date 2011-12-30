package com.walmart.fraudengine.web.validation;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.walmart.fraudengine.util.LogSupport;

public class ValidateUtils {

	private static String MSG_NOT_BLANK = "field could not be blank";
	private static String MSG_NOT_EMPTY = "field could not be empty";
	private static String MSG_NOT_NULL = "missing required field";
	private static String MSG_NUMBER_OUT_OF_RANGE = "the number is out of range";
	private static String MSG_STRING_INCORRECT_LENGTH = "the string is too short or too long";
	private static String MSG_EMAIL_FORMAT = "field should be in format of email";
	private static String MSG_DIGITS = "field should be digits";
	private static String MSG_MATCH_PATTERN = "field's value doesn't match the specific pattern";

	private static String CONSTAINT_NOT_BLANK = "not blank field";
	private static String CONSTAINT_NOT_EMPTY = "not empty field";
	private static String CONSTAINT_NOT_NULL = "not null field";
	private static String CONSTAINT_NUMBER_RANGE = "range stricted number";
	private static String CONSTAINT_STRING_LENGTH = "length stricted string";
	private static String CONSTAINT_EMAIL = "email field";
	private static String CONSTAINT_DIGITS = "digit field";
	private static String CONSTAINT_PATTERN = "pattern matching field";

	private static boolean VALID = true;
	private static boolean INVALID = false;

	public static boolean notBlank(ValidateErrors errors, String fieldName,
			String value) {
		
		if(value == null){
			LogSupport.warn("parameter[value] is null.");
		}
		
		if (value != null && StringUtils.isBlank(value)) {
			errors.addError(fieldName, MSG_NOT_BLANK, CONSTAINT_NOT_BLANK);
			return INVALID;
		}
		
		return VALID;
	}

	public static boolean notEmpty(ValidateErrors errors, String fieldName,
			String value) {
		
		if(value == null){
			LogSupport.warn("parameter[value] is null.");
		}
		
		if (value != null && StringUtils.isEmpty(value)) {
			errors.addError(fieldName, MSG_NOT_EMPTY, CONSTAINT_NOT_EMPTY);
			return INVALID;
		}
		
		return VALID;
	}

	public static boolean notNull(ValidateErrors errors, String fieldName,
			Object value) {
		
		if (value == null) {
			errors.addError(fieldName, CONSTAINT_NOT_NULL, MSG_NOT_NULL);
			return INVALID;
		}
		
		return VALID;
	}

	public static boolean range(ValidateErrors errors, String fieldName,
			Number value, Number min, Number max) {
		
		if(value == null){
			LogSupport.warn("parameter[value] is null.");
		}
		if(min == null){
			LogSupport.warn("parameter[min] is null.");
		}
		if(max == null){
			LogSupport.warn("parameter[max] is null.");
		}
		
		if (value != null && max != null && min != null
				&& value.doubleValue() <= max.doubleValue()
				&& value.doubleValue() >= min.doubleValue()) {
			errors.addError(fieldName, MSG_NUMBER_OUT_OF_RANGE,
					CONSTAINT_NUMBER_RANGE);
			return INVALID;
		}
		
		return VALID;
	}

	public static boolean length(ValidateErrors errors, String fieldName,
			String value, int min, int max) {
		
		if(value == null){
			LogSupport.warn("parameter[value] is null.");
		}
		
		if (value != null && value.length() <= max && value.length() >= min) {
			errors.addError(fieldName, MSG_STRING_INCORRECT_LENGTH,
					CONSTAINT_STRING_LENGTH);
			return INVALID;
		}
		
		return VALID;
	}

	public static boolean email(ValidateErrors errors, String fieldName,
			String value) {
		
		if(value == null){
			LogSupport.warn("parameter[value] is null.");
		}
		
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(check);
		if (value != null && !pattern.matcher(value).matches()) {
			errors.addError(value, MSG_EMAIL_FORMAT, CONSTAINT_EMAIL);
			return INVALID;
		}
		
		return VALID;
	}

	public static boolean digits(ValidateErrors errors, String fieldName,
			Object value) {
		
		if(value == null){
			LogSupport.warn("parameter[value] is null.");
		}
		
		if (value != null) {
			boolean isNumber = false;
			int index = value.toString().indexOf(",");
			if (index >= 0) {
				isNumber = value.toString().matches(
						"[+-]?[1-9]+[0-9]+(,[0-9]{3})+(.[0-9]+)?");
			} else {
				isNumber = value.toString().matches(
						"[+-]?[1-9]+[0-9]+(.[0-9]+)?");
			}
			if (!isNumber) {
				errors.addError(fieldName, MSG_DIGITS, CONSTAINT_DIGITS);
				return INVALID;
			}
		}
		
		return VALID;
	}

	public static boolean pattern(ValidateErrors errors, String fieldName,
			String value, String patternStr) {
		
		if(StringUtils.isEmpty(patternStr)){
			LogSupport.warn("parameter[patterStr] could not be empty.");
		}
		
		if(value == null){
			LogSupport.warn("parameter[value] is null.");
		}
		
		if (value != null && patternStr != null
				&& !fieldName.matches(patternStr)) {
			errors.addError(fieldName, MSG_MATCH_PATTERN, CONSTAINT_PATTERN);
			return INVALID;
		}
		
		return VALID;
	}
}
