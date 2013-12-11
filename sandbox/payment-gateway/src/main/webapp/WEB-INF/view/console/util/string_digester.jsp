<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<script type="text/javascript" src="<c:url value='/console/js/jquery-1.7.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/console/js/jquery.form.js'/>"></script>

<script>
	$(function() 
	{
		var digestFormOptions = {
			target: '#digester'              // target element(s) to be updated with server response
			// ,url:   'console/initialize'  // override form's 'action' attribute
			// ,beforeSubmit: showRequest    // pre-submit callback
			// ,success:      showResponse   // post-submit callback
		}

		$("#digestForm").submit(function()
		{
			$(this).ajaxSubmit(digestFormOptions);
			return false;
		});
	});
</script>

	<div id="digester">
		<div>
			<p>Please enter a String for which you want to generate a String Digest:</p>
			<form id="digestForm" action="<c:url value='/console/util/string_digester'/>" 
			method="POST">
				<input name="encryptMe"
				size="48"/> 
				<input type="submit" value="Enter"/>
			</form>
		</div>
		<div>
			<c:if test="${!empty stringDigest}">
			<p>String Digest for '${encryptMe}' is: <b>${stringDigest}</b></p>
			</c:if>
		</div>
	</div>
<!-- vim: set ft=html :-->
