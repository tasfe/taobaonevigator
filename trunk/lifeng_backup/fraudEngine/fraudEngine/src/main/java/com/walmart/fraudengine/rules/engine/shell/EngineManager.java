package com.walmart.fraudengine.rules.engine.shell;

import java.io.StringReader;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walmart.fraudengine.dao.BusRuleDao;
import com.walmart.fraudengine.exception.ErrorCode;
import com.walmart.fraudengine.exception.FraudEngineApplicationException;
import com.walmart.fraudengine.model.BusRuleEntity;
import com.walmart.fraudengine.model.BusRulePriorityEntity;
import com.walmart.fraudengine.model.BusRulePropertyEntity;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.LogSupport;

@Component 
public final class EngineManager {
	//static injection
	private static BusRuleDao busRuleDaoJpa;

	
    @Autowired(required = true)
    private EngineManager(BusRuleDao busRuleDaoJpa) {
    	EngineManager.busRuleDaoJpa = busRuleDaoJpa;
    }

    private static class Holder {  
    	  static final KnowledgeBase instance = getInstance();  
    } 
    
	public static KnowledgeBase getKnowledgeBase(){
		return Holder.instance;
	}

	private static KnowledgeBase getInstance(){
		try {
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
					.newKnowledgeBuilder();
			List<BusRulePriorityEntity> busRulePriorityList = busRuleDaoJpa.findAllBusRulePriority();
			if(busRulePriorityList == null || busRulePriorityList.size()==0){
				return null;
			}
			int priority = 0;
			for(BusRulePriorityEntity rulePriority : busRulePriorityList){
				BusRuleEntity rule = rulePriority.getBusRule();
				String ruletoken  = replaceTokens(rule.getRule(),rule.getBusRulePk());
				ruletoken = replaceSalience(ruletoken,priority++);
				ruletoken = replaceRuleId(ruletoken,rule.getBusRulePk());
				LogSupport.debug(ruletoken);
				kbuilder.add(ResourceFactory.newReaderResource(new StringReader(ruletoken)),
						ResourceType.DRL);
			}
			if (kbuilder.hasErrors()) {
				LogSupport.error("Error when building production memory: "
						+ kbuilder.getErrors().toString());
				throw new FraudEngineApplicationException(ErrorCode.CANNOT_PARSE_RULE_FILE);
			}
			KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
			kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
			return kbase;
		} catch (Exception e) {
			LogSupport.error("Error build Rule Engine KnowledgeBase!",e);
			return null;
		}
	}
	
	
	//replace the String
	private static String replaceTokens(String ruleFile, Long busRulePk){
		List<BusRulePropertyEntity> propertyList = busRuleDaoJpa.findBusRulePropertyByRuleId(busRulePk);
		if (propertyList == null || propertyList.size() == 0)
			return ruleFile;
		for (BusRulePropertyEntity property : propertyList) {
			ruleFile = ruleFile.replaceFirst(FraudCheckConst.BUS_RULE_TOKEN_PREFIX+property.getPropertyName()
					+FraudCheckConst.BUS_RULE_TOKEN_SUFFIX,
					property.getPropertyValue());
		}
		return ruleFile;

	}
	
	//replace the salience
	private static String replaceSalience(String ruleFile, int priority){
		ruleFile = ruleFile.replaceFirst(FraudCheckConst.BUS_RULE_SALIENCE, FraudCheckConst.BUS_RULE_SALIENCE+" "+priority);
		return ruleFile;
	}
	
	//replace the rule_id
	private static String replaceRuleId(String ruleFile, long ruleId){
		ruleFile = ruleFile.replaceFirst(FraudCheckConst.BUS_RULE_ID, ""+ruleId);
		return ruleFile;
	}
	

}
