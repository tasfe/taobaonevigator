package com.walmart.fraudengine.util;

public class FraudCheckConst {
	static public final String DENY_STATUS = "DENY";
	
	static public final String ACCEPT_STATUS = "ACCEPT";
	static public final String ACCEPT_REASON_CODE = "2000";
	static public final String NO_RULE_HIT_REASON_DESC = "NO ANY RULE HAS BEEN HIT";
	static public final String CHALLENGE_STATUS = "CHALLENGE";
	static public final String NO_INFO_STATUS = "NO_INFORMATION";
	static public final String REASON_FOR_OMS_CANCEL = "CANCELLED BY OMS";
	static public final String ACCEPT_REASON_BY_SWITCH_OFF = "ACCEPT BY 3rd PARTY SWITCH";
	static public final String ERROR_MESSAGE = "ERROR";
	static public final String STATUS_ACTIVE = "ACTIVE";
	
	static public final String STATUS_INACTIVE = "INACTIVE";
	static public final String CANCELLED_STATUS = "CANCELLED";
	static public final String CANCELLED_REASON = "REASON FOR CANCELLED";
	static public final String CSC_CANCELLED = "ORDER_CANCELLED_ACK";
	

	
	//==========================Neg/Pos filter =======================
	
	static public final String CUSTOEMR_ID = "CUSTOMER_ID";
	
	static public final String CUSTOMER_ADDRESS = "BILLING_ADDRESS";
	
	static public final String CUSTOMER_EMAIL = "EMAIL_ADDRESS";
	
	static public final String SHIPPING_ADDRESS = "SHIPPING_ADDRESS";
	
	static public final String SHIPPING_PHONE = "PHONE_NUMBER";
	
	static public final String IP_ADDRESS = "IP_ADDRESS";
	
	//We have not got the const from the table
	static public final String CUSTOMER_ACCOUNT_AGE = "";
	
	static public final String SHIPPING_EMAIL = "";
	
	static public final String SHIPPING_METHOD = "";
	
	//=================================================================
	
	static public final String ENTERPRISE_CODE = "DEFAULT";
	static public final String DOCUMENT_TYPE_RETURN = "0003";
	static public final String DOCUMENT_TYPE = "0001";
	
	static public final String CREATE_BY = "SYSTEM";
	static public final String LAST_MODIFIED_BY = "SYSTEM";
//	static public final String SOFT_DEL_NO = "N"; 
	static public final String ADDRESS_TYPE = "SHIPPING";
	static public final String CSC_SERVER_NAME = "FRAUD_MACHINE";
	static public final String OMS_ORDER_DEFAULT_PROVINCE = "SHANGHAI";
	static public final String NULL_VALUE = " ";
	
	public static final String CSC_RESPONSE_PACKAGE = "com.walmart.fraudengine.jaxb.csc.response";
	public static final String OMS_RESPONSE_PACKAGE = "com.walmart.fraudengine.jaxb.oms.response";
	public static final String CSC_REQUEST_PACKAGE = "com.walmart.fraudengine.jaxb.csc.request";
	public static final String OMS_REQUEST_PACKAGE = "com.walmart.fraudengine.jaxb.oms.request";
	
	public static final String UTF_8 = "UTF-8";
	
	public static final String BUS_RULE_TOKEN_PREFIX = "\\$\\{";
	public static final String BUS_RULE_TOKEN_SUFFIX = "\\}";
	public static final String BUS_RULE_TOKEN = "\\^param";
	
	public static final String BUS_RULE_SALIENCE = "salience";
	public static final String BUS_RULE_ID = "\\^pk";
	
	public static final String INCLUDE_SOFT_DELETE = "Y";
	public static final String EXCLUDE_SOFT_DELETE = "N";
	
	public static final String ORDER_ASC = "ASC";
	public static final String ORDER_DESC = "DESC";
	
	//============================PATH================================
	
	//path of fraudEngine.properties
	public static final String PATH_FRAUDENGINE_PROPERTIES ="/fraudEngine.properties";
	
	public static final String PATH_FRAUDENGINE_SQL = "/META-INF/jpql/FraudEngineFormNativeQueries.xml";
	
	
	//====================== For Properties ==========================
	
	//Tibco Queue settings
	public static final String KEY_TIBCO_URL = "tibco.url";
	public static final String KEY_TIBCO_CONNECTION_FACTORY = "tibco.connectionFactory";
	
	//======================   DataSource ============================
	public static final String JNDI_DATASOURCE = "java:OracleDS"; 
	
	
	//======================= Error code ==============================
	public static final String UNIQUE_CONSTRAINTS_EXCEPTION = "UNIQUE_CONSTRAINTS_EXCEPTION";
	
	//front end result
	public static final String OPERATOR_SUCCESS = "SUCCESS";
	
	//======================= Web constants ==============================
	//common
	public static final String WEB_PARAM_EMPTY_STRING = "";
	//business rule pages
	public static final String WEB_PARAM_BUSRULE_PAGE = "page";
	public static final String WEB_PARAM_BUSRULE_MAX = "max";
	public static final String WEB_PARAM_BUSRULE_SORD = "sord";
	public static final String WEB_PARAM_BUSRULE_SORX = "sidx";
	public static final String WEB_PARAM_BUSRULE_SORX_TOKEN = "grid_";
	public static final String WEB_PARAM_BUSRULE_COUNT = "count";
	public static final String WEB_PARAM_BUSRULE_ID = "id";
	public static final String WEB_PARAM_BUSRULE_STATUSMAP = "statusMap";
	public static final String WEB_PARAM_BUSRULE_ERRORS = "errors";
	public static final String WEB_PARAM_BUSRULE_RULEPKS = "rulePKs";
	public static final String WEB_PARAM_BUSRULE_MESSAGE = "message";
	public static final String WEB_PARAM_BUSRULE_FORM = "busRuleForm";
}
