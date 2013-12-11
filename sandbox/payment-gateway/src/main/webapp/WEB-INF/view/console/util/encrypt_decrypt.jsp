<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<script type="text/javascript" src="<c:url value='/console/js/jquery-1.7.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/console/js/jquery.form.js'/>"></script>

<script>
	$(function() 
	{
		var encryptFormOptions = {
			target: '#encryptor'              // target element(s) to be updated with server response
			// ,beforeSubmit: showRequest    // pre-submit callback
			// ,success:      showResponse   // post-submit callback
		}

		$("#encryptForm").submit(function()
		{
			$(this).ajaxSubmit(encryptFormOptions);
			return false;
		});
	});
</script>

	<div id="encryptor">
		<div>
			<ul>
				<c:forEach items="${messageList}" var="message">  
					<li>${message}</li>
				</c:forEach>   
			</ul>
			<form id="encryptForm" action="<c:url value='/console/util/encrypt_decrypt'/>" method="POST">
				<p>Please enter an Encryption Key:</p>
				<input name="encryptKey" size="48" value="${encryptKey}"/> 
				<p>Please enter a String that you want to Encrypt:</p>
				<input name="encryptMe" size="48"/> 
				<input type="submit" value="Encrypt"/>
				<p>Please enter a String that you want to Decrypt:</p>
				<input name="decryptMe" size="48"/> 
				<input type="submit" value="Decrypt"/>
			</form>
		</div>
		<div>
			<c:if test="${!empty encryptedString}">
			<p>Encrypted String for '${encryptMe}' is: <b>${encryptedString}</b></p>
			</c:if>
		</div>
		<div>
			<c:if test="${!empty decryptedString}">
			<p>Decrypted String for '${decryptMe}' is: <b>${decryptedString}</b></p>
			</c:if>
		</div>
	</div>
<!-- vim: set ft=html :-->
