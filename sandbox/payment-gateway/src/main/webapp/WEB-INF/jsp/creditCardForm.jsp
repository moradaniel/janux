<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<h3>Credit Card Capture Test Form</h3>

	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.3/jquery-ui.min.js"></script>

	<script type="text/javascript" src="<c:url value='/resources/scripts/jquery.validate/1.7/jquery.validate.js'/>"></script>

	<script type="text/javascript" src="<c:url value='/resources/scripts/payConstants.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/scripts/payCreditCardForm.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/scripts/payCC.js'/>"></script>

	<script type="text/javascript">
		$(document).ready(function(){
		/**
		 * $.fn.loadCreditCardForm = function(uuid,cardNumber,cardholderName,type,expiration,line1,line1,zip,city,phone) {
		 */
		function cancel()
		{
			alert("the credit card was not able to save ");
		}
		
		function	callback(data)
		{
			alert("the credit card was saved with uuid "+  data.creditCard.uuid);
		};
		
		$("#pay_creditCardForm").creditCardForm('load',callback,cancel,'DEFAULT','');
		});
	</script>
	
<body>
	<div id="pay_creditCardForm"></div>
</body>
</html>
