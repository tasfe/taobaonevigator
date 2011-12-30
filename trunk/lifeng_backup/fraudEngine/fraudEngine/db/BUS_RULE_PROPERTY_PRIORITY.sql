
Insert into BUS_RULE (BUS_RULE_PK,RULE_FILE_NAME,RULE,RULE_DESCRIPTION,START_DATE,END_DATE,STATUS_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION,REASON_CODE) values (145,'accept_order_amount_less_than_x.drl',
'package com.walmart.fraudengine.rules.resources.drl;

import com.walmart.fraudengine.rules.RuleEngineOrder;
import com.walmart.fraudengine.rules.RuleOrderStatus;
import com.walmart.fraudengine.util.FraudCheckConst;

global RuleOrderStatus orderStatus;

rule "Accept_Order_Amount_Less_Than_X_CNY"
  salience 
  when
    $order: RuleEngineOrder(calculateAmountSubtotal < ^param)
  then
    orderStatus.setOrderScore(FraudCheckConst.ACCEPT_STATUS);
    orderStatus.setReasonDesc("Accept_Order_Amount_Less_Than_X_CNY");
    orderStatus.setReasonCode("2000");
    orderStatus.setRuleId("^pk");
    drools.halt();
end','Accept where Order Total  < x',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),6,'N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1,'2000');

Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE (BUS_RULE_PK,RULE_FILE_NAME,RULE,RULE_DESCRIPTION,START_DATE,END_DATE,STATUS_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION,REASON_CODE) values (146,'accept_category_has_y_line_quantity_less_than_x.drl','package com.walmart.fraudengine.rules.resources.drl;

import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.rules.RuleEngineOrder;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.DroolsUtil;
import com.walmart.fraudengine.rules.RuleOrderStatus;

global RuleOrderStatus orderStatus;

rule "accept_category_has_y_line_quantity_less_than_x"
  salience 
  when
    $order: Order()
    $ruleOrder: RuleEngineOrder(originalOrder == $order)
    eval(DroolsUtil.getOrderQuantitiesByCategory($order, "^param" ,true) < ^param)
  then
    orderStatus.setOrderScore(FraudCheckConst.ACCEPT_STATUS);
    orderStatus.setReasonDesc("accept_category_has_y_line_quantity_less_than_x");
    orderStatus.setReasonCode("2000");
    orderStatus.setRuleId("^pk");
    drools.halt();
end','Accept where Order Line Quantity < x and Category=y',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),6,'N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1,'2000');

Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE (BUS_RULE_PK,RULE_FILE_NAME,RULE,RULE_DESCRIPTION,START_DATE,END_DATE,STATUS_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION,REASON_CODE) values (147,'accept_category_has_y_line_charge_less_than_x.drl','package com.walmart.fraudengine.rules.resources.drl;

import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.rules.RuleEngineOrder;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.DroolsUtil;
import com.walmart.fraudengine.rules.RuleOrderStatus;

global RuleOrderStatus orderStatus;

rule "accept_category_has_y_line_charge_less_than_x"
  salience 
  when
    $order: Order()
    $ruleOrder: RuleEngineOrder(originalOrder == $order)
    eval(DroolsUtil.getAmountByCategory($order, "^param" ,true) < ^param)
  then
    orderStatus.setOrderScore(FraudCheckConst.ACCEPT_STATUS);
    orderStatus.setReasonDesc("accept_category_has_y_line_charge_less_than_x");
    orderStatus.setReasonCode("2000");
    orderStatus.setRuleId("^pk");
    drools.halt();
end','Accept where Order Line Amount < x and Category=y',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),6,'N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1,'2000');

Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE (BUS_RULE_PK,RULE_FILE_NAME,RULE,RULE_DESCRIPTION,START_DATE,END_DATE,STATUS_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION,REASON_CODE) values (148,'challenge_order_amount_greater_than_x.drl','package com.walmart.fraudengine.rules.resources.drl;

import com.walmart.fraudengine.rules.RuleEngineOrder;
import com.walmart.fraudengine.rules.RuleOrderStatus;
import com.walmart.fraudengine.util.FraudCheckConst;

global RuleOrderStatus orderStatus;

rule "Challenge_Order_Amount_Greater_Than_X_CNY"
  salience 
  when
    $order: RuleEngineOrder(calculateAmountSubtotal > ^param)
  then
    orderStatus.setOrderScore(FraudCheckConst.CHALLENGE_STATUS);
    orderStatus.setReasonDesc("Challenge_Order_Amount_Greater_Than_X_CNY");
    orderStatus.setReasonCode("5000");
    orderStatus.setRuleId("^pk");
    drools.halt();
end','Challenge where Order Total  > x',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),6,'N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1,'5000');

Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE (BUS_RULE_PK,RULE_FILE_NAME,RULE,RULE_DESCRIPTION,START_DATE,END_DATE,STATUS_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION,REASON_CODE) values (149,'challenge_category_has_z_line_quantity_greater_than_x.drl','package com.walmart.fraudengine.rules.resources.drl;

import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.rules.RuleEngineOrder;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.DroolsUtil;
import com.walmart.fraudengine.rules.RuleOrderStatus;

global RuleOrderStatus orderStatus;

rule "challenge_category_has_z_line_quantity_greater_than_x"
  salience 
  when
    $order: Order()
    $ruleOrder: RuleEngineOrder(originalOrder == $order)
    eval(DroolsUtil.getOrderQuantitiesByCategory($order, "^param" , false) > ^param)
  then
    orderStatus.setOrderScore(FraudCheckConst.CHALLENGE_STATUS);
    orderStatus.setReasonDesc("challenge_category_has_z_line_quantity_greater_than_x");
    orderStatus.setReasonCode("5000");
    orderStatus.setRuleId("^pk");
    drools.halt();
end','Challenge where Order Line Quantity > x and Category=z',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),6,'N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1,'5000');

Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE (BUS_RULE_PK,RULE_FILE_NAME,RULE,RULE_DESCRIPTION,START_DATE,END_DATE,STATUS_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION,REASON_CODE) values (150,'challenge_category_has_z_line_charge_greater_than_x.drl','package com.walmart.fraudengine.rules.resources.drl;

import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.rules.RuleEngineOrder;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.DroolsUtil;
import com.walmart.fraudengine.rules.RuleOrderStatus;

global RuleOrderStatus orderStatus;

rule "challenge_category_has_z_line_charge_greater_than_x"
  salience 
  when
    $order: Order()
    $ruleOrder: RuleEngineOrder(originalOrder == $order)
    eval(DroolsUtil.getAmountByCategory($order, "^param" ,false) > ^param)
  then
    orderStatus.setOrderScore(FraudCheckConst.CHALLENGE_STATUS);
    orderStatus.setReasonDesc("challenge_category_has_z_line_charge_greater_than_x");
    orderStatus.setReasonCode("5000");
    orderStatus.setRuleId("^pk");
    drools.halt();
end','Challenge where Order Line Amount > x and Category=z',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),6,'N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1,'5000');

Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE (BUS_RULE_PK,RULE_FILE_NAME,RULE,RULE_DESCRIPTION,START_DATE,END_DATE,STATUS_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION,REASON_CODE) values (151,'deny_order_amount_greater_than_x.drl','package com.walmart.fraudengine.rules.resources.drl;

import com.walmart.fraudengine.rules.RuleEngineOrder;
import com.walmart.fraudengine.rules.RuleOrderStatus;
import com.walmart.fraudengine.util.FraudCheckConst;

global RuleOrderStatus orderStatus;

rule "Deny_Order_Amount_Greater_Than_X_CNY"
  salience 
  when
    $order: RuleEngineOrder(calculateAmountSubtotal > ^param)
  then
    orderStatus.setOrderScore(FraudCheckConst.DENY_STATUS);
    orderStatus.setReasonDesc("Deny_Order_Amount_Greater_Than_X_CNY");
    orderStatus.setReasonCode("8000");
    orderStatus.setRuleId("^pk");
    drools.halt();
end','Deny where Order Total  > x',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),6,'Y',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1,'8000');

Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE (BUS_RULE_PK,RULE_FILE_NAME,RULE,RULE_DESCRIPTION,START_DATE,END_DATE,STATUS_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION,REASON_CODE) values (152,'deny_category_has_z_line_quantity_greater_than_x.drl','package com.walmart.fraudengine.rules.resources.drl;

import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.rules.RuleEngineOrder;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.DroolsUtil;
import com.walmart.fraudengine.rules.RuleOrderStatus;

global RuleOrderStatus orderStatus;

rule "deny_category_has_z_line_quantity_greater_than_x"
  salience 
  when
    $order: Order()
    $ruleOrder: RuleEngineOrder(originalOrder == $order)
    eval(DroolsUtil.getOrderQuantitiesByCategory($order, "^param",false) > ^param)
  then
    orderStatus.setOrderScore(FraudCheckConst.DENY_STATUS);
    orderStatus.setReasonDesc("deny_category_has_z_line_quantity_greater_than_x");
    orderStatus.setReasonCode("8000");
    orderStatus.setRuleId("^pk");
    drools.halt();
end','Deny where Order Line Quantity > x and Category=z',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),6,'N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1,'8000');

Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE (BUS_RULE_PK,RULE_FILE_NAME,RULE,RULE_DESCRIPTION,START_DATE,END_DATE,STATUS_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION,REASON_CODE) values (153,'deny_category_has_z_line_charge_greater_than_x.drl','package com.walmart.fraudengine.rules.resources.drl;

import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.rules.RuleEngineOrder;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.DroolsUtil;
import com.walmart.fraudengine.rules.RuleOrderStatus;

global RuleOrderStatus orderStatus;

rule "deny_category_has_z_line_charge_greater_than_x"
  salience 
  when
    $order: Order()
    $ruleOrder: RuleEngineOrder(originalOrder == $order)
    eval(DroolsUtil.getAmountByCategory($order, "^param" ,false) > ^param)
  then
    orderStatus.setOrderScore(FraudCheckConst.DENY_STATUS);
    orderStatus.setReasonDesc("deny_category_has_z_line_charge_greater_than_x");
    orderStatus.setReasonCode("8000");
    orderStatus.setRuleId("^pk");
    drools.halt();
end','Deny where Order Line Amount > x and Category=z',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),6,'N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1,'8000');


Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE (BUS_RULE_PK,RULE_FILE_NAME,RULE,RULE_DESCRIPTION,START_DATE,END_DATE,STATUS_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION,REASON_CODE) values (154,'Deny_Order_Amount_Greater_Than_X_CNY_And_MOP_Is_Z.drl','package com.walmart.fraudengine.rules.resources.drl;

import com.walmart.fraudengine.rules.RuleEngineOrder;
import com.walmart.fraudengine.rules.RuleOrderStatus;
import com.walmart.fraudengine.util.FraudCheckConst;

global RuleOrderStatus orderStatus;

rule "Deny_Order_Amount_Greater_Than_X_CNY_And_MOP_Is_Z"
  salience
  when
    $order: RuleEngineOrder(calculateAmountSubtotal > ^param , queryPaymentMethod == "^param")
  then
    orderStatus.setOrderScore(FraudCheckConst.DENY_STATUS);
    orderStatus.setReasonDesc("Deny_Order_Amount_Greater_Than_X_CNY");
    orderStatus.setReasonCode("8000");
    orderStatus.setRuleId("^pk");
    drools.halt();
end','Deny where Order Total > x and MOP=z',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),6,'N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1,'8000');



Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE (BUS_RULE_PK,RULE_FILE_NAME,RULE,RULE_DESCRIPTION,START_DATE,END_DATE,STATUS_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION,REASON_CODE) values (155,'challenge_category_has_z_line_quantity_greater_than_x_And_MOP_Is_Y.drl','package com.walmart.fraudengine.rules.resources.drl;

import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.rules.RuleEngineOrder;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.DroolsUtil;
import com.walmart.fraudengine.rules.RuleOrderStatus;

global RuleOrderStatus orderStatus;

rule "challenge_category_has_z_line_quantity_greater_than_x_And_MOP_Is_Y"
  salience
  when
    $order: Order()
    $ruleOrder: RuleEngineOrder(originalOrder == $order , queryPaymentMethod == "^param")
    eval(DroolsUtil.getOrderQuantitiesByCategory($order, "^param" , false) > ^param)
  then
    orderStatus.setOrderScore(FraudCheckConst.CHALLENGE_STATUS);
    orderStatus.setReasonDesc("challenge_category_has_z_line_quantity_greater_than_x");
    orderStatus.setReasonCode("5000");
    orderStatus.setRuleId("^pk");
    drools.halt();
end','Challenge where Order Line Quantity > x and Category=z and MOP=Y',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),6,'Y',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1,'8000');




Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PRIORITY (BUS_RULE_PRIORITY_PK,PRIORITY,BUS_RULE_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (41,29,145,'N',to_timestamp('05-AUG-11 02.44.57.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PRIORITY (BUS_RULE_PRIORITY_PK,PRIORITY,BUS_RULE_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (42,30,146,'N',to_timestamp('05-AUG-11 02.44.57.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PRIORITY (BUS_RULE_PRIORITY_PK,PRIORITY,BUS_RULE_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (43,31,147,'N',to_timestamp('05-AUG-11 02.44.57.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PRIORITY (BUS_RULE_PRIORITY_PK,PRIORITY,BUS_RULE_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (44,32,148,'N',to_timestamp('05-AUG-11 02.44.57.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PRIORITY (BUS_RULE_PRIORITY_PK,PRIORITY,BUS_RULE_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (45,33,149,'N',to_timestamp('05-AUG-11 02.44.57.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PRIORITY (BUS_RULE_PRIORITY_PK,PRIORITY,BUS_RULE_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (46,34,150,'N',to_timestamp('05-AUG-11 02.44.57.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PRIORITY (BUS_RULE_PRIORITY_PK,PRIORITY,BUS_RULE_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (47,35,151,'Y',to_timestamp('05-AUG-11 02.44.57.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PRIORITY (BUS_RULE_PRIORITY_PK,PRIORITY,BUS_RULE_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (48,36,152,'N',to_timestamp('05-AUG-11 02.44.57.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PRIORITY (BUS_RULE_PRIORITY_PK,PRIORITY,BUS_RULE_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (49,37,153,'N',to_timestamp('05-AUG-11 02.44.57.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PRIORITY (BUS_RULE_PRIORITY_PK,PRIORITY,BUS_RULE_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (50,38,154,'N',to_timestamp('05-AUG-11 02.44.57.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PRIORITY (BUS_RULE_PRIORITY_PK,PRIORITY,BUS_RULE_FK,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (51,39,155,'Y',to_timestamp('05-AUG-11 02.44.57.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);



Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (21,145,'10','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (23,146,'Moives','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (24,146,'4','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (26,147,'Videos','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (27,147,'499','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (29,148,'5000','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (31,149,'Moives','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (32,149,'5','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (34,150,'Videos','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (35,150,'500','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (37,151,'20000','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (39,152,'Moives','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (40,152,'50','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (42,153,'Videos','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (43,153,'1000','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (44,155,'ALIPAY','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (45,155,'Moives','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (46,155,'5','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (47,154,'20000','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
Insert into CN_OMS_FRAUD_ENGINE.BUS_RULE_PROPERTY (BUS_RULE_PROPERTY_PK,BUS_RULE_FK,PROPERTY_VALUE,SOFT_DELETE,CREATED_DATE,CREATED_BY,LAST_MODIFIED_DATE,LAST_MODIFIED_BY,VERSION) values (48,154,'ALIPAY','N',to_timestamp('04-AUG-11 11.56.38.000000000 PM','DD-MON-RR HH.MI.SS.FF AM'),'SYSTEM',null,null,1);
commit;



