var CONFIG = (function() {
	
	//pattern to protect the variables against modification
	var private = {
			'gateway.urlServerHost':'${gateway.urlServerHost}',
			'gateway.urlServerPort':'${gateway.urlServerPort}',
			'gateway.urlServerProtocol':'${gateway.urlServerProtocol}',
			'gateway.urlServerContext':'${gateway.urlServerContext}',
			'gateway.urlServer': '',
				 
			//urls in controllers
			'url.CreditCardSecureControllerRest':'${url.CreditCardSecureControllerRest}',
			'url.CreditCardTypeControllerRest':'${url.CreditCardTypeControllerRest}',
			'url.CountryControllerRest':'${url.CountryControllerRest}',
			'url.StateControllerRest':	 '${url.StateControllerRest}'
	};
	
	private['gateway.urlServer'] = private['gateway.urlServerProtocol']+"://"+private['gateway.urlServerHost']+":"+private['gateway.urlServerPort']+"/"+private['gateway.urlServerContext']; 

	return {
		get: function(name) { return private[name]; }
	};
})();
