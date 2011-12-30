package com.walmart.fraudengine.web.form;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;

import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.LogSupport;
import com.walmart.fraudengine.web.validation.Validatable;
import com.walmart.fraudengine.web.validation.ValidateErrors;
import com.walmart.fraudengine.web.validation.ValidateUtils;

public class BusRuleForm implements Validatable{
	
	private Long rulePK;
	
	private String description;
	
	private String fileName;
	
	private Integer priority;
	
	private Timestamp startDate;
	
	private Timestamp endDate;

	private Integer hit;
	
	private String rule;
	
	private String status;
	
	public static final String FETCH_ALL = "FETCH_ALL";
	
	private Map<String, String> properties;
	
	public Long getRulePK() {
		return rulePK;
	}
	
	public void setRulePK(Long rulePK) {
		this.rulePK = rulePK;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getStatus() {
		return status;
	}
	
	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Integer getPriority() {
		return priority;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public Map<String, String> getProperties() {
		return properties;
	}
	
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public Integer getHit() {
		return hit;
	}

	public void setHit(Integer hit) {
		this.hit = hit;
	}

	@Override
	public ValidateErrors validate() {
		ValidateErrors errors = new ValidateErrors();
		ValidateUtils.notBlank(errors, "fileName", this.fileName);
		ValidateUtils.notBlank(errors, "description", this.description);
		this.validateRule(errors);
		return errors;
	}
	
	//TODO need a interface in EngineManager
	private boolean validateRule(ValidateErrors errors) {
		if ( StringUtils.isBlank(rule)) {
			LogSupport.warn("rule is blank.");
			return false;
		}
//		String ruleFile = rule;
//        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//        //priority
//        ruleFile = ruleFile.replaceFirst(FraudCheckConst.BUS_RULE_SALIENCE, 
//        		FraudCheckConst.BUS_RULE_SALIENCE + " " + priority);
//        //ruleId
//        ruleFile = ruleFile.replaceFirst(FraudCheckConst.BUS_RULE_ID, "1");// use "1" to do a validation
//        //properties --update by adding property name for each property
//        if (properties != null && !properties.isEmpty()) {
//        	for(Map.Entry<String,String> property:properties.entrySet()){
//        		ruleFile = ruleFile.replaceAll(FraudCheckConst.BUS_RULE_TOKEN_PREFIX+
//    					property.getPropertyName()+FraudCheckConst.BUS_RULE_TOKEN_SUFFIX,
//    					property.getPropertyValue());
//        	}
//        }
//        kbuilder.add(ResourceFactory.newReaderResource(
//        		new StringReader(ruleFile)), ResourceType.DRL);
//        if (kbuilder.hasErrors()) {
//			LogSupport.error("Error when parsing the drl file!" + kbuilder.getErrors());
//			errors.addError("rule", "validate rule file", 
//					"Error when parsing the drl file!" + kbuilder.getErrors());
//			return false;
//		}
        return true;
	}
}
