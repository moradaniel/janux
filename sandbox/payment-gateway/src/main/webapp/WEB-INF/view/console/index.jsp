<%//response.sendRedirect("console/initialize");%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>


<html>
<head>
	<meta charset="utf-8">
	<title>Payment Gateway Console</title>

	<link   type="text/css" href="<c:url value='/console/css/redmond/jquery-ui-1.8.17.custom.css'/>" rel="stylesheet">
	<link   type="text/css" href="<c:url value='/console/css/console.css'/>" rel="stylesheet">
	<script type="text/javascript" src="<c:url value='/console/js/jquery-1.7.1.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/console/js/jquery-ui-1.8.17.custom.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/console/js/jquery.form.js'/>"></script>

	<script>
	$(function() 
	{
		$("#tabs").tabs(
		{
			ajaxOptions: {
				error: function( xhr, status, index, anchor ) {
					$( anchor.hash ).html(
						"Couldn't load this tab. We'll try to fix this as soon as possible. " +
						"If this wouldn't be a demo." );
				}
			}
		});

	});
	</script>
</head>

<body>
<h1>Payment Gateway Console</h1>
<div class="demo">

<div id="tabs">
	<ul>
		<li><a href="initialize">Status</a></li>
		<li><a href="util/string_digester">Key Digest Util</a></li>
		<li><a href="util/encrypt_decrypt">Encrypt/Decrypt Util</a></li>
	</ul>

</div>

</div><!-- End demo -->

</body>
</html>
<!-- vim: set ft=html :-->
