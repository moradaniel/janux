(function($){

	var urlServer = CONFIG.get('gateway.urlServer'); 
	var currentState="";
	var currentCountry="";
	var currentCreditCardType="";
	var editingCreditCard=false;
  
	var JSON = JSON || {};
	  
	//implement JSON.stringify serialization
	JSON.stringify = JSON.stringify || function (obj) {
		var t = typeof (obj);
		if (t != "object" || obj === null) {
			// simple data type
			if (t == "string") obj = '"'+obj+'"';
			return String(obj);
		}
		else {
			// recurse array or object
			var n, v, json = [], arr = (obj && obj.constructor == Array);
			for (n in obj) {
				v = obj[n]; t = typeof(v);
				if (t == "string") v = '"'+v+'"';
				else if (t == "object" && v !== null) v = JSON.stringify(v);
				json.push((arr ? "" : '"' + n + '":') + String(v));
			}
			return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
		}
	};

	//jQuery(document).ajaxStart(setHeader);

	$.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
  	// Modify options, control originalOptions, store jqXHR, etc
  	setHeader(jqXHR);
	});

	function setHeader(jqXHR) {
		jqXHR.setRequestHeader('pay_audit_domain', $("#pay_audit_domain").val());
		jqXHR.setRequestHeader('pay_audit_business_unit', $("#pay_audit_business_unit").val());
		jqXHR.setRequestHeader('pay_audit_user', $("#pay_audit_user").val());
	}	

	function addValidators() {
	
		$.validator.addMethod(
		"customDate",
		function(value, element) {
		    return value.match(/^\d\d?\/\d\d\d\d$/);
		},
			"Please enter a date in the format mm/y Ex: 01/2020"
		);
		
		$.validator.addMethod(
		"checkCreditCardNumber",
		function(value, element) {
			//TODO add a function to get the cc type from cc number
		var type = pay_get_cctype(value);
		//var type = "";
		if (type==null)
		{
			return false;
		}
		else
		{
			var select = $("#pay_type").get()[0];
		    	for(var i=0; i < select.options.length; i++) {
				  if(select.options[i].text == type) {
				     select.options[i].selected = true;
				     break;
				  }
				}
		    	return true;
		    }
		},
			"Credit card number appears to be invalid - please retry"
		);
		
		$("#pay_creditCardForm").validate({
		  rules: {
		    pay_cardNumber: {
		      required: true,
		      checkCreditCardNumber: true
		    },
		    pay_expiration: {
		      required: true,
		      customDate : true
		    }
		  }
		});
	}
  
  function createHTMLObjects(rootElement)
  {
  	rootElement.append("<form id='pay_creditCardForm'></form>");

  	$("#pay_creditCardForm").append("<input type='hidden' id='pay_businessUnitCode' name='pay_businessUnitCode'>");
	$("#pay_creditCardForm").append("<input type='hidden' id='pay_uuid' name='pay_uuid'>");
	$("#pay_creditCardForm").append("<input type='hidden' id='pay_audit_domain' name='pay_audit_domain'>");
	$("#pay_creditCardForm").append("<input type='hidden' id='pay_audit_business_unit' name='pay_audit_business_unit'>");
	$("#pay_creditCardForm").append("<input type='hidden' id='pay_audit_user' name='pay_audit_user'>");
	$("#pay_creditCardForm").append("<table id='pay_creditCardTable'></table>");
	$("#pay_creditCardTable").append("<tr><td>Credit Card #</td><td><input type='text' id='pay_cardNumber' name='pay_cardNumber'></input></td></tr>");
	$("#pay_creditCardTable").append("<tr><td>Expiration</td><td><input type='text' id='pay_expiration' name='pay_expiration'></input></td></tr>");

	var select ="<select id='pay_type' name='pay_type'></select>";
	$("#pay_creditCardTable").append("<tr><td>Credit Card Type</td><td>"+select+"</td></tr>");
	
	$("#pay_creditCardTable").append("<tr><td>Holders Name</td><td><input type='text' id='pay_cardholderName' name='pay_cardholderName'></input></td></tr>");
	$("#pay_creditCardTable").append("<tr><td>Address 1</td><td><input type='text' id='pay_postalAddressLine1' name='pay_postalAddressLine1'></input></td></tr>");
	$("#pay_creditCardTable").append("<tr><td>Address 2</td><td><input type='text' id='pay_postalAddressLine2' name='pay_postalAddressLine2'></input></td></tr>");
	$("#pay_creditCardTable").append("<tr><td>Zip</td><td><input type='text' id='pay_postalAddressPostalCode' name='pay_postalAddressPostalCode'></input></td></tr>");
	$("#pay_creditCardTable").append("<tr><td>City</td><td><input type='text' id='pay_postalAddressCity' name='pay_postalAddressCity'></input></td></tr>");
	$("#pay_creditCardTable").append("<tr><td>Country</td><td><select id='pay_postalAddressCountry' name='pay_postalAddressCountry'></select></td></tr>");
	$("#pay_creditCardTable").append("<tr><td>State</td><td><select id='pay_postalAddressStateProvince' name='pay_postalAddressStateProvince'></select></td></tr>");
	
	$("#pay_creditCardTable").append("<tr><td><input id='pay_save' name='pay_save' value='Save' type='button'></input><input id='pay_cancel' name='pay_cancel' value='Cancel' type='button'></input></td></tr>");
	
  }
  
  
  function addEventsButton(callback,cancel)
  {
  	$( "#pay_save").click(function() {
  		if ($("#pay_creditCardForm").valid())
		{
  			if (editingCreditCard)
  				$("#pay_cardNumber").val('');
  			var jsonObjects ={"creditCard":
  	    	{
  	    		"uuid":$("#pay_uuid").val(),
  	    		"cardholderName":$("#pay_cardholderName").val(),
  	    		"number":$("#pay_cardNumber").val(),
  	    		"expiration":$("#pay_expiration").val(),
  	    		"typeCode":$("#pay_type").val(),
  				"billingAddress":
  					{
  	    				"line1":$("#pay_postalAddressLine1").val(),
  	    	    		"line2":$("#pay_postalAddressLine2").val(),
  	    	    		"city":$("#pay_postalAddressCity").val(),
  	    	    		"stateProvince":$("#pay_postalAddressStateProvince").val(),
  	    	    		"postalCode":$("#pay_postalAddressPostalCode").val(),
  	    	    		"country":$("#pay_postalAddressCountry").val()
  					}
  	    	}
  			};
  			
  			$.getJSON(urlServer+CONFIG.get('url.CreditCardSecureControllerRest')+".jsonp?save&callback=?&businessUnitCode="+$("#pay_businessUnitCode").val()+"&autoCreateBusinessUnit=false&pay_uuid="+$("#pay_uuid").val(),
			  {
				creditCard: JSON.stringify(jsonObjects)
			  },
			  function(data) {
			    callback(data);
			  });
		}
	});
	
	$( "#pay_cancel").click(function() {
		    cancel();
	});		
  }
  
  function setValues(pay_audit_domain,pay_audit_user,pay_businessUnitCode,pay_uuid,pay_cardNumber,pay_cardholderName,pay_type,pay_expiration,pay_postalAddressLine1,pay_postalAddressLine2,pay_postalAddressPostalCode,pay_postalAddressCity,pay_postalAddressCountry,pay_postalAddressStateProvince)
  {
  	/*set the inital values*/
	$("#pay_uuid").val(pay_uuid);
	$("#pay_cardNumber").val(pay_cardNumber);
	$("#pay_expiration").val(pay_expiration);
	$("#pay_type").val(pay_type);
	$("#pay_cardholderName").val(pay_cardholderName);
	$("#pay_postalAddressLine1").val(pay_postalAddressLine1);
	$("#pay_postalAddressLine2").val(pay_postalAddressLine2);
	$("#pay_postalAddressPostalCode").val(pay_postalAddressPostalCode);
	$("#pay_postalAddressCity").val(pay_postalAddressCity);
	$("#pay_businessUnitCode").val(pay_businessUnitCode);
	$('#pay_audit_domain').val(pay_audit_domain);
	$('#pay_audit_business_unit').val(pay_businessUnitCode);
	$('#pay_audit_user').val(pay_audit_user);
	currentCreditCardType = pay_type;
	currentState = pay_postalAddressStateProvince;
	currentCountry = pay_postalAddressCountry;
	
	if (pay_uuid!=''){
		$("#pay_cardNumber").attr('disabled','disabled');
		$("#pay_expiration").attr('disabled','disabled');
		$("#pay_type").attr('disabled','disabled');
	}
	
	$('#pay_cardNumber').focus();
  }
  
  function loadCreditCardTypes(success)
  {
  	/**
	* build the credit card types
	*/
	$.getJSON(urlServer+CONFIG.get('url.CreditCardTypeControllerRest')+".jsonp?creditCardTypes&callback=?",
		{
		},
		function(data) {
		  		var options = '';
		  		for (var i = 0; i < data.creditCardTypes.length; i++) {
		    		options += '<option value="' + data.creditCardTypes[i].code + '">' + data.creditCardTypes[i].description + '</option>';
		  		}
		  		$("select#pay_type").html(options);
		  		
		  		try {
				  $("select#pay_type option:first").attr('selected', 'selected');
				}
				catch(ex) {
				    setTimeout("$('select#pay_type option:first').attr('selected', 'selected')",1);
				}
		  		
		  		select = $("#pay_type").get()[0];
				for(var i=0; i < select.options.length; i++) {
					if(select.options[i].value == currentCreditCardType) {
						try {
						     select.options[i].selected = true;
					    }
						catch(ex) {
						    setTimeout("select.options["+i+"].selected = true",1);
						}
					    break;
					}
				};
		  		
		  		success($("select#pay_type option:selected").val());

		  		if (jQuery.browser.msie) {
    				jQuery("#" + "pay_type").css("width", "100%").css('width', 'auto');
				}
		  		
		}
	);
  }
  
  function loadCountries(success)
  {
  	/**
	* build the countries
	*/
	$.getJSON(urlServer+CONFIG.get('url.CountryControllerRest')+".jsonp?countries&callback=?",
		{
		},
		function(data) {
		  		var options = '';
		  		for (var i = 0; i < data.countries.length; i++) {
		    		options += '<option value="' + data.countries[i].code + '">' + data.countries[i].name + '</option>';
		  		}
		  		$("select#pay_postalAddressCountry").html(options);
		  		
		  		try {
				  $("select#pay_postalAddressCountry option:first").attr('selected', 'selected');
				}
				catch(ex) {
				    setTimeout("$('select#pay_postalAddressCountry option:first').attr('selected', 'selected')",1);
				}
		  		
		  		select = $("#pay_postalAddressCountry").get()[0];
				for(var i=0; i < select.options.length; i++) {
				  if(select.options[i].value == currentCountry || select.options[i].text == currentCountry) {
				     
					try {
						select.options[i].selected = true;
					}
					catch(ex) {
					    setTimeout("select.options["+i+"].selected = true",1);
					}
				    break;
				  }
				};
		  		
		  		success($("select#pay_postalAddressCountry option:selected").val());

		  		if (jQuery.browser.msie) {
    				jQuery("#" + "pay_postalAddressCountry").css("width", "100%").css('width', 'auto');
				}
		  		
		}
	);
  }
  
  function loadStates(countryCode,success)
  {
  	$.getJSON(urlServer+CONFIG.get('url.StateControllerRest')+".jsonp?states&callback=?",
	{
	 		"countryCode":countryCode
	},
 	function(data) {
   		var options = '';
   		for (var i = 0; i < data.states.length; i++) {
     			options += '<option value="' + data.states[i].code + '">' + data.states[i].name + '</option>';
   		}
   		$("select#pay_postalAddressStateProvince").html(options);
   		
   		
   		var select2 = $("#pay_postalAddressStateProvince").get()[0];
		for(var i=0; i < select2.options.length; i++) {
	  		if(select2.options[i].value == currentState || select2.options[i].text == currentState) {
	     		select2.options[i].selected = true;
	     		
	     		try {
					select2.options[i].selected = true;
			    }
				catch(ex) {
				    setTimeout("select2.options["+i+"].selected = true",1);
				}
	     		break;
	  		}
		};
		
		if (jQuery.browser.msie) {
  			jQuery("#" + "pay_postalAddressStateProvince").css("width", "100%").css('width', 'auto');
		}
		
		success();
 	})
  }
  
  
  var methods = {
    load : function(callback,cancel,pay_audit_domain,pay_audit_user,pay_businessUnitCode,pay_uuid,pay_cardNumber,pay_cardholderName,pay_type,pay_expiration,pay_postalAddressLine1,pay_postalAddressLine2,pay_postalAddressPostalCode,pay_postalAddressCity,pay_postalAddressCountry,pay_postalAddressStateProvince) {  
	
	createHTMLObjects(this);
	addValidators();

    setValues(pay_audit_domain,pay_audit_user,pay_businessUnitCode,pay_uuid,pay_cardNumber,pay_cardholderName,pay_type,pay_expiration,pay_postalAddressLine1,pay_postalAddressLine2,pay_postalAddressPostalCode,pay_postalAddressCity,pay_postalAddressCountry,pay_postalAddressStateProvince);
	
	loadCreditCardTypes(function(){});	
	loadCountries(
		function (selectedCountryCode)
		{
			loadStates(selectedCountryCode,function(){});
		}
	);
	
	$("select#pay_postalAddressCountry").change(
		function()
		{	
			loadStates($("select#pay_postalAddressCountry option:selected").val(),function(){})
		}
	);
	
	addEventsButton(callback,cancel);
    
	
	return this.each(function() {});
    },
    set : function(callback,cancel,pay_audit_domain,pay_audit_user,pay_uuid,decrypt) {
	
		if (pay_uuid!='')
		   {
			editingCreditCard=true;
			functionName ="load";
			
			if (decrypt=="true")
				functionName ="decrypt";
			
			/**
			*get the credit card by uuid
			*/
			
			$.getJSON(urlServer+CONFIG.get('url.CreditCardSecureControllerRest')+".jsonp?"+functionName+"&callback=?",
			  {
			  	"pay_uuid":pay_uuid
			  },
			  function(data) {
				pay_businessUnitCode= data.creditCard.businessUnitCode;
				if (decrypt=="true")
					pay_cardNumber= data.creditCard.number;
				else
					pay_cardNumber= data.creditCard.numberMasked;
			 	pay_cardholderName = data.creditCard.cardholderName;
			 	pay_type=data.creditCard.typeCode;
			 	pay_expiration=data.creditCard.expiration;
			 	pay_postalAddressLine1=data.creditCard.billingAddress.line1;
			 	pay_postalAddressLine2=data.creditCard.billingAddress.line2;
			 	pay_postalAddressPostalCode=data.creditCard.billingAddress.postalCode;
			 	pay_postalAddressCity=data.creditCard.billingAddress.city;
			 	pay_postalAddressCountry=data.creditCard.billingAddress.country;
			 	pay_postalAddressStateProvince=data.creditCard.billingAddress.stateProvince;
			 	
			 	setValues(pay_audit_domain,pay_audit_user,pay_businessUnitCode,pay_uuid,pay_cardNumber,pay_cardholderName,pay_type,pay_expiration,pay_postalAddressLine1,pay_postalAddressLine2,pay_postalAddressPostalCode,pay_postalAddressCity,pay_postalAddressCountry,pay_postalAddressStateProvince);
			 	
				loadCountries(
					function (selectedCountryCode)
					{
						loadStates(selectedCountryCode,function(){});
					}
				);
		
			  });
			
		}
		else
		{
			editingCreditCard=false;
		}
	
	return this.each(function() {});
	},
	save : function(callback,cancel,pay_audit_domain,pay_audit_user,pay_businessUnitCode,pay_uuid,pay_cardNumber,pay_cardholderName,pay_type,pay_expiration,pay_postalAddressLine1,pay_postalAddressLine2,pay_postalAddressPostalCode,pay_postalAddressCity,pay_postalAddressCountry,pay_postalAddressStateProvince) {  
	
		var jsonObjects ={"creditCard":
	    {
	    		"uuid":pay_uuid,
	    		"cardholderName":pay_cardholderName,
	    		"number":pay_cardNumber,
	    		"expiration":pay_expiration,
	    		"typeCode":pay_type,
				"billingAddress":
					{
	    				"line1":pay_postalAddressLine1,
	    	    		"line2":pay_postalAddressLine2,
	    	    		"city":pay_postalAddressCity,
	    	    		"stateProvince":pay_postalAddressStateProvince,
	    	    		"postalCode":pay_postalAddressPostalCode,
	    	    		"country":pay_postalAddressCountry
					}
	    	}
			};
  			
  			$.getJSON(urlServer+CONFIG.get('url.CreditCardSecureControllerRest')+".jsonp?save&callback=?&businessUnitCode="+pay_businessUnitCode+"&autoCreateBusinessUnit=false&pay_uuid="+pay_uuid,
			  {
				creditCard: JSON.stringify(jsonObjects)
			  },
			  function(data) {
			    callback(data);
			  });
	
		return this.each(function() {});
	}
  };


  $.fn.creditCardForm = function( method ) {
    
    // Method calling logic
    if ( methods[method] ) {
      return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    } else if ( typeof method === 'object' || ! method ) {
      return methods.init.apply( this, arguments );
    } else {
      $.error( 'Method ' +  method + ' does not exist on jQuery.creditCardForm' );
    }    
  
  };


})(jQuery);

