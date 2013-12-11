<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<script type="text/javascript" src="<c:url value='/console/js/jquery-1.7.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/console/js/jquery.form.js'/>"></script>

<div id="status">

<!-- <p>isAppInit: ${isAppInit}</p> -->

<c:if test="${!isAppInit}">
<script>
	$(function() 
	{
		var initFormOptions = {
			target: '#status'             // target element(s) to be updated with server response
			// ,url:   'console/initialize'  // override form's 'action' attribute
			// ,beforeSubmit: showRequest    // pre-submit callback
			// ,success:      showResponse   // post-submit callback
		}


		$("#initForm").submit(function()
		{
			$(this).ajaxSubmit(initFormOptions);
			return false;
		});


		function showRequest(formData, jqForm, options) 
		{
			alert("About to submit: \n\n" + $.param(formData));
			return true;
		}
		
		function showResponse(responseText, statusText, xhr, $form) 
		{
			alert("status: " + statusText + "\n\nresponseText: \n" + responseText);
		}
	});
</script>

		<div>
			<h3>Payment Gateway Initialization</h3>
			<p>The Payment Gateway has not been initialized and is not operational.</p>
			<p>Please enter the Password Encryption Key to initialize the application:</p>
			<ul>
				<c:forEach items="${messageList}" var="message">  
					<li>${message}</li>
				</c:forEach>   
			</ul>
			<form id="initForm" action="<c:url value='/console/initialize'/>" method="POST">
				<input name="passEncryptKey" size="24" value="initializeMe"/> 
				<input type="hidden" name="submitted" value="1"/> 
				<input type="submit" value="Enter Key"/>
			</form>
		</div>
</c:if>

<c:if test="${isAppInit}">
<ul style="line-height: 200%">
	<c:forEach items="${messageList}" var="message">  
		<li>${message}</li>
	</c:forEach>   
		<li><a href="<c:url value='/app/creditCard.json?ping'/>">PING the Payment Gateway</li>
</ul>
</c:if>
</div>
<!-- vim: set ft=html: -->
