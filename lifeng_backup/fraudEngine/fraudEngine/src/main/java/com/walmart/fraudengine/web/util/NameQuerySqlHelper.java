package com.walmart.fraudengine.web.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walmart.fraudengine.jaxb.jpql.EntityMappings;
import com.walmart.fraudengine.jaxb.jpql.NamedQuery;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.web.JaxbXmlConverter;

@Component
public class NameQuerySqlHelper {
	
    @Autowired
    private JaxbXmlConverter jaxbXmlConverter;
    
    private  Map<String,String> sqlMap = null ;
    
    
    /**
    * Get the predefined sql by name 
     * @param name
    * @return
    * @throws Exception 
     */
    public  String getSqlByName(String name) {
    	if(sqlMap == null){
            sqlMap = new HashMap<String,String>();
            loadConfiguration(FraudCheckConst.PATH_FRAUDENGINE_SQL);
        }
        if(!sqlMap.containsKey(name))
                throw new RuntimeException("predefined sql not found ! name :"+name);
        return sqlMap.get(name);
    }
    
    /**
    * Parameter 
     * @param path
    */
    private void loadConfiguration(String path){
    	EntityMappings em = null;
    	try {
    		em = jaxbXmlConverter.convertJpql(path);
		} catch (IOException e1) {
			throw new RuntimeException("JPQL filePath not found ! filePath:"+path);
		}
    	List<NamedQuery> nameQueryList = em.getNamedQuery();
		if(nameQueryList == null)return;
		Iterator<NamedQuery> iter = nameQueryList.iterator();
		while(iter.hasNext()){
			NamedQuery query = iter.next();
			sqlMap.put(query.getName(), query.getQuery());
		}
    }
        
        
}

