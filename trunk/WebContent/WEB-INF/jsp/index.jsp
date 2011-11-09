<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="promotion" items="${promotions}">
	<tr>
		<td>${promotion.name}</td>
		<td>${promotion.picture}</td>
		<td>${promotion.enabled}</td>
		<td>${promotion.id}</td>
		<td>${promotion.catagory.name}</td>
	</tr><br/>
</c:forEach>
