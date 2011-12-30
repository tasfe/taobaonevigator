package com.walmart.fraudengine.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.jaxb.oms.request.OrderLine;

public final class DroolsUtil {
	//TODO
	public static int getOrderAmount(Order order){
		return 0;
	}
	//TODO
	public static double getOrderQuantities(Order order) throws Exception{
		double orderQty = 0;
		try{
			if(null != order&& null != order.getOrderLines()){
				List<OrderLine> orderLineList = order.getOrderLines().getOrderLine(); 
				for(OrderLine orderLine : orderLineList){
				if(null != orderLine && orderLineList.size()>0){
						orderQty += orderLine.getOrderedQty();
					}
				}
				}	
		}catch(Exception e){
			LogSupport.error("There are some exceptions in getOrderQuantities method"+e.getMessage());
			throw e; 
		}
		
		return orderQty;
	}
	//TODO
	public static int getOrderLineNo(Order order) throws Exception{
		int lineNo = 0;
		try{
		if(null != order && null != order.getOrderLines()){
		 lineNo = order.getOrderLines().getOrderLine().size();
		}
		}catch(Exception e){
			LogSupport.error("There are some exceptions in getOrderLineNo method"+e.getMessage());
			throw e; 
		}
		return lineNo;
	}
	//TODO
	public static Set<String> getItemCategory(Order order) throws Exception{
		Set<String> set = new HashSet<String>();
		try{
		if(null != order&& null != order.getOrderLines()){
			List<OrderLine> orderLineList = order.getOrderLines().getOrderLine(); 
			for(OrderLine orderLine : orderLineList){
			if(null != orderLine && orderLineList.size()>0 && null != orderLine.getItem()){
					set.add(orderLine.getItem().getItemCategory());
				}
			}
			}	
		}catch(Exception e){
			LogSupport.error("There are some exceptions in getItemCategory method"+e.getMessage());
			throw e; 
		}
		return set;
	}
	//TODO
	public static int getOrderLineNoByCategory(Order order, String categoryName,boolean isAccept) throws Exception{
		int orderLineNo = -1;
		try{
			if(null != order&& null != order.getOrderLines()){
			List<OrderLine> orderLineList = order.getOrderLines().getOrderLine(); 
			for(OrderLine orderLine : orderLineList){
			if(null != orderLine && orderLineList.size()>0 && null != orderLine.getItem()){
				if(categoryName.equalsIgnoreCase(orderLine.getItem().getItemCategory())){
					orderLineNo = orderLineList.size();
				}
			}
			}	
			}
		}catch(Exception e){
			LogSupport.error("There are some exceptions in getOrderLineNoByCategory method"+e.getMessage());
			throw e; 
		}
		if(orderLineNo<0&&isAccept)return Integer.MAX_VALUE;
		return orderLineNo;
		
	}
		
	//TODO
	public static int getOrderQuantitiesByCategory(Order order,String categoryName,boolean isAccept) throws Exception{
		int orderQuantities = 0;
		boolean hasFound = false;
		try{
			if(null != order&& null != order.getOrderLines()){
			List<OrderLine> orderLineList = order.getOrderLines().getOrderLine(); 
			for(OrderLine orderLine : orderLineList){
				if(null != orderLine && orderLineList.size()>0 && null != orderLine.getItem()){
					if(categoryName.equalsIgnoreCase(orderLine.getItem().getItemCategory())){
						hasFound = true;
						orderQuantities += orderLine.getOrderedQty();
					}
				}
				}
			}
		}catch(Exception e){
			LogSupport.error("There are some exceptions in getOrderQuantities ByCategory method"+e.getMessage());
			throw e; 
		}
		if(!hasFound&&isAccept)return Integer.MAX_VALUE;
		return orderQuantities ;
	}
	//TODO
	public static double getAmountByCategory(Order order, String categoryName,boolean isAccept) throws Exception{
		double amount = 0;
		boolean hasFound = false;
		try{
			if(null != order&& null != order.getOrderLines()){
				List<OrderLine> orderLineList = order.getOrderLines().getOrderLine(); 
				for(OrderLine orderLine : orderLineList){
					if(null != orderLine && orderLineList.size()>0 && null != orderLine.getItem()){
						if(categoryName.equalsIgnoreCase(orderLine.getItem().getItemCategory())){
							hasFound = true;
							amount += orderLine.getLinePriceInfo().getLineTotal();
						}
					}
				}
			}
			LogSupport.debug("Order line charge with " + categoryName + " is " + amount);
		}catch(Exception e){
			LogSupport.error("There are some exceptions in getAmountByCategory method"+e.getMessage());
			throw e; 
		}
		if(!hasFound&& isAccept)return Double.MAX_VALUE;
		return amount;
		
	}
	//TODO
	public static double getAmountByPaymentMethod(Order order, String paymentMethod){
		
		return order.getPriceInfo().getTotalAmount();
	}
}	
