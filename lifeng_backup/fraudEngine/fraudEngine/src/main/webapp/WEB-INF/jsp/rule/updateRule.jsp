<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../common/common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="rule.update.title"/></title>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/calendar/js/jscal2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/calendar/js/lang/en.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/calendar/css/jscal2.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/calendar/css/border-radius.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/calendar/css/steel/steel.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/theme.css"/>
<style type="text/css">
#submit,#cancel,#th {
                          font-family:"arial",Georgia,Serif;
                          font-size:65%;
          }
</style>

<script type="text/javascript">
	$(function(){
		//$("#th").button();
		$("input:submit").button();
		$("input:button").button();
	});
	
	var propertiesNum = 0;
	function validation(){
		var pattern =/^\d{1,8}$/;
		var pattern1 = /^.{1,100}$/;
		var pattern2 = /^.{1,200}$/;
		var errorMsg = "";
		
		var ruleName = document.getElementById("ruleName").value;
		var errorName = document.getElementById("errorName").value;
		if(!pattern1.exec(ruleName)){
			errorMsg+=errorName;
		}
		var ruleDesc = document.getElementById("ruleDesc").value;
		var errorDescription1 = document.getElementById("errorDescription1").value;
		var errorDescription2 = document.getElementById("errorDescription2").value;
		if(!pattern2.exec(ruleDesc)){
			if(""==errorMsg){
			errorMsg+=errorDescription1;	
			}else{
			errorMsg+=errorDescription2;
			}
		}
		var priority = document.getElementById("rulePriority").value;
		var errorPriority1 = document.getElementById("errorPriority1").value;
		var errorPriority2 = document.getElementById("errorPriority2").value;
		if(!pattern.exec(priority)){
			if(""==errorMsg){
			errorMsg+=errorPriority1;	
			}else{
			errorMsg+=errorPriority2;
			}
		}
		var errorProperty1 = document.getElementById("errorProperty1").value;
		var errorProperty2 = document.getElementById("errorProperty2").value;
		for(var i = 0; i<propertiesNum;i++){
			var eleValue = document.getElementById("properties"+i).value;
			if(!pattern1.exec(eleValue)){
				if(""==errorMsg){
					errorMsg+=errorProperty1;	
					}else{
					errorMsg+=errorProperty2;
					}
			}
		}
		var illegal = document.getElementById("illegal");
		if(""!=errorMsg){
		document.getElementById('message').innerHTML = errorMsg+illegal;
		return false;
		}
		window.close();
		return true;
	}
</script>
</head>
<body>
	<div id='message' style="color:red;font-size:20px;" align="left"></div>
	<input type="hidden"  id="illegal" value="<spring:message code="rule.update.illegal"/>"  />
	
	<input type="hidden"  id="errorName" value="<spring:message code="rule.update.errorName"/>"  />
	<input type="hidden"  id="errorDescription1" value="<spring:message code="rule.update.errorDescription1"/>"  />
	<input type="hidden"  id="errorDescription2" value="<spring:message code="rule.update.errorDescription2"/>"  />
	<input type="hidden"  id="errorPriority1" value="<spring:message code="rule.update.errorPriority1"/>"  />
	<input type="hidden"  id="errorPriority2" value="<spring:message code="rule.update.errorPriority2"/>"  />
	<input type="hidden"  id="errorProperty1" value="<spring:message code="rule.update.errorProperty1"/>"  />
	<input type="hidden"  id="errorProperty2" value="<spring:message code="rule.update.errorProperty2"/>"  />
	<form:form modelAttribute="busRuleForm" name="form"
		enctype="multipart/form-data" method="post"
		action="updateBusRule.html" commandName="busRuleForm"
		onsubmit="return validation()">
		<table  id="tt" class="listTable">
			<tr id="th"><td><spring:message code="rule.update.column1"/></td><td align="left"><spring:message code="rule.update.column2"/></td>
			</tr>
			<tr>
				<td><spring:message code="rule.update.pk"/></td>
				<td><form:input path="rulePK" disabled="disabled" readonly="true" />
				</td>
			</tr>
			

			<tr>
				<td><spring:message code="rule.update.name"/>:<font color="red"><form:errors
							path="fileName" />
				</font>
				</td>
				<td><form:input path="fileName" id="ruleName" width="400"/>
				</td>
			</tr>
			
			<tr>
				<td><spring:message code="rule.update.description"/>:<font color="red"><form:errors
							path="description" />
				</font>
				</td>
				<td><form:textarea path="description" id="ruleDesc"/>
				</td>
			</tr>
			<tr>
				<td><spring:message code="rule.update.priority"/>:<font color="red"><form:errors
							path="priority" />
				</font>
				</td>
				<td><form:input path="priority" id ="rulePriority"/>
				</td>
			</tr>
			

			<tr>
				<td><spring:message code="rule.update.startDate"/>:<font color="red"><form:errors
							path="startDate" />
				</font>
				</td>
				<td><form:input path="startDate" id="startDate" readonly = "true"/>
					<button id="startDateBtn">...</button></td>
			</tr>

			<tr>
				<td><spring:message code="rule.update.endDate"/>:<font color="red"><form:errors
							path="endDate" />
				</font>
				</td>
				<td><form:input path="endDate" id="endDate" readonly = "true"/>
					<button id="endDateBtn">...</button></td>
			</tr>
			
			<tr>
				<td><spring:message code="rule.update.status"/>:<font color="red"><form:errors
							path="status" />
				</font>
				</td>
				<td><form:select path="status">
						<form:options items="${statusMap}" />
					</form:select></td>
			</tr>

			<c:forEach var="property" items="${busRuleForm.properties}"
				varStatus="loop">
				<script>
					propertiesNum =  ${loop.index}+1;
				</script>
				<tr>
					<td><spring:message code="rule.update.property"/>:</td>
					<td><form:input path="properties[${loop.index}]"/></td>
				</tr>
			</c:forEach>
			<%-- <spring:bind path="properties">
			<c:forEach var="property1" items="${busRuleForm.properties}" varStatus="loop1">
			<tr>
				<td>Property1:</td>
			</tr>
			<tr>
				<td><form:input path="${property1}" />
			</td>
			</tr>
			</c:forEach>
			</spring:bind> --%>
			<tr><td></td><td></td></tr>
			<tr>
				<td><input id="submit" type="submit" value="<spring:message code="rule.update.updateBtn"/>" /></td>
				<td><input id="cancel" type="button" value="<spring:message code="rule.update.close"/>" onclick="window.close()" />				
				</td>
			</tr>


		</table>
	</form:form>
	<script language="JavaScript">

			      var cal = Calendar.setup({
			          onSelect: function(cal) { cal.hide() },
			          showTime: true
			      });
			      cal.manageFields("startDateBtn", "startDate", "%Y-%m-%d %k:%M:%S");
			      cal.manageFields("endDateBtn", "endDate", "%Y-%m-%d %k:%M:%S");
	</script>

</body>
</html>