<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
	<head>
	<title>Login Page</title>
</head>
<body onload='document.login.j_username.focus();'>

<h3>Account Login</h3>
<c:if test="${param.state == 'bye'}">
<p style="color:green">You have successfully logged out from the application.
<br/>For your security, we recommend that you close your browser.
</p >
<p>To login again, please enter your credentials below.</p>
</c:if>

<form name='login' action="<c:url value='/j_spring_security_check'/>" method='POST'>
 <table>
    <tr><td>Account Name:</td><td><input type='text' name='j_username' value=''></td></tr>
    <tr><td>Password:</td><td><input type='password' name='j_password'/></td></tr>
    <tr><td align="right" colspan='2'><input value="Log in" type="submit"/></td></tr>
  </table>
</form>
<c:if test="${param.state == 'failure'}">
<p style="color:red">The Credentials that you entered are not valid, please try again.</p>
</c:if>

</body>
</html>
