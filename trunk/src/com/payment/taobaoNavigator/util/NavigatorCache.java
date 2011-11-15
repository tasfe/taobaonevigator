package com.payment.taobaoNavigator.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.payment.taobaoNavigator.entity.CatagoryEntity;
import com.payment.taobaoNavigator.entity.ProductEntity;
import com.payment.taobaoNavigator.service.NavigatorService;

public abstract class NavigatorCache {
	private static NavigatorService navigatorServiceImpl;
	static {
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");

		navigatorServiceImpl = (NavigatorService) ac.getBean("navigatorServiceImpl");
		
	}
	private static Map<String, List<CatagoryEntity>> NavigatorCache = new HashMap<String, List<CatagoryEntity>>();

	private static List<CatagoryEntity> contain(int pid) {
		return NavigatorCache.get(String.valueOf(pid));
	}

	private static void cleanCache() {
		NavigatorCache.clear();
	}

	private static void cleanChildCache(int parentId) {
		NavigatorCache.remove(parentId);
	}

	private static void addNavigatorCache(int parentId, List<CatagoryEntity> childList) {
		NavigatorCache.put(String.valueOf(parentId), childList);
	}

	private static List<CatagoryEntity> processQuery(int parentId) {
		List<CatagoryEntity> childList = contain(parentId);
		if (null == childList || 0 == childList.size()) {
			childList = navigatorServiceImpl.findCatagoryByPid(parentId);
			//remove the root get itself...
//			java.util.ConcurrentModificationException
			if(1==parentId){
			 for (Iterator it = childList.iterator(); it.hasNext();) {
				 CatagoryEntity child=(CatagoryEntity)it.next();
				 if(child.getId()==0){
						it.remove();
					} 
			 }
			}
			addNavigatorCache(parentId, childList);
		}
		return childList;
	}

	private static List<CatagoryEntity> processADD(int parentId) {
		return null;
	}

	private static List<CatagoryEntity> processUpdate(int parentId) {
		return null;
	}

	private static List<CatagoryEntity> processDelete(int parentId) {
		return null;
	}

	public static List<CatagoryEntity> processAction(String actionName, int parentId) {
		List<CatagoryEntity> catagorys = new LinkedList<CatagoryEntity>();
		if ("add".equals(actionName)) {
			catagorys = processADD(parentId);
		} else if ("update".equals(actionName)) {
			catagorys = processUpdate(parentId);
		} else if ("delete".equals(actionName)) {
			catagorys = processDelete(parentId);
		} else if ("query".equals(actionName)) {
			catagorys = processQuery(parentId);
		}
		return catagorys;
	}

	/**
	 * just for the index navigator
	 * 
	 * @param parentId
	 * @return
	 */
	public static List<CatagoryEntity> getByParentId(int parentId) {
		return processAction("query", parentId);
	}

	public static CatagoryEntity createDefaultCatagoryEntity() {
		CatagoryEntity result = new CatagoryEntity();
		return result;
	}
	
	public Map<CatagoryEntity, List<ProductEntity>> getHotProducts(int parentId,int maxNumber) {
		return navigatorServiceImpl.getHotProducts(parentId,maxNumber);
	}
	
	 

}
