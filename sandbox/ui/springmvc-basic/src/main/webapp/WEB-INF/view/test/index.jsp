<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page isELIgnored="false" %>
<html>
	<head>
		<title>Test Home</title>
	</head>
	<body>
		<h1>Test Home</h1>
		<a href="<c:url value='/basic'/>">Basic Home</a> | 
		<a href="<c:url value='/logout'/>">Log Out</a>
	</body>
</html>
