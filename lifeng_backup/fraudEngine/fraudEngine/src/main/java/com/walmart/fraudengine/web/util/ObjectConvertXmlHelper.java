package com.walmart.fraudengine.web.util;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import javassist.Modifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walmart.fraudengine.jaxb.jquery.Row;
import com.walmart.fraudengine.jaxb.jquery.Rows;
import com.walmart.fraudengine.util.LogSupport;
import com.walmart.fraudengine.web.JaxbXmlConverter;

@Component("objectConvertXmlHelper")
public class ObjectConvertXmlHelper {
	
	@Autowired
	private JaxbXmlConverter jaxbXmlConverter;
	


	public <T> String createObjectXml(List<T> list, long count, long page) {
		Rows rows = new Rows();
		rows.setTotal(Math.ceil(count / (page * 1d)));
		rows.setRecords(count);
		Iterator<T> iter = list.iterator();
		for (int i = 0; iter.hasNext(); i++) {
			T ele = iter.next();
			Row row = new Row();
			row.setId(i+"");
			Class<? extends Object> cls = ele.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				if(!Modifier.isPrivate(field.getModifiers()))continue;
				try {
					field.setAccessible(true);
					Object fieldValue = field.get(ele);
					row.getCell().add(fieldValue+"");
				} catch (Exception e) {
					LogSupport.error("",e);
				}
			}
			rows.getRow().add(row);
		}
		return jaxbXmlConverter.createJqueryXml(rows);
	}


}
