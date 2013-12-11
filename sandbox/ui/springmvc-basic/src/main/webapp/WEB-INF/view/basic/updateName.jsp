<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<div>
	<div>
		<form id="updateName" action="<c:url value='/basic/updateName'/>" method="POST">
			<input name="firstName" size="24" value="${firstName}"/><br/>
			<input name="lastName"  size="24" value="${lastName}"/><br/>
			<input type="submit" value="Enter Name"/>
		</form>
		<p>
		<c:if test="${!empty(firstName)}">
		${firstName} ${lastName} is a cool name
		</c:if>
		</p>
	</div>

</div>
<!-- vim: set ft=html: -->
