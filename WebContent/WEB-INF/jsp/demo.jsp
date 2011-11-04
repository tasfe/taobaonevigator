<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<base href="<%=request.getContextPath() %>">
<title>This is for Demo!</title>
</head>
<body>
	<h1>God, Let's start.</h1>
	<form:form commandName="demoForm" method="post" action="demo.do">
		<form:input type="text" path="demoField" />
		<input type="submit" name="submit" value="DEMO" />
		<input type="reset" value="RESET" />
	</form:form>
</body>
</html>