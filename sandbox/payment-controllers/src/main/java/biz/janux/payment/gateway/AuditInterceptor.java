package biz.janux.payment.gateway;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 ***************************************************************************************************
 * This interceptor 
 * 
 * @author  <a href="mailto:albertobuffagni@@gmail.com">Alberto Buffagni</a>
 * @since 0.4.0 - 2012-03-13
 ***************************************************************************************************
 */
public class AuditInterceptor extends HandlerInterceptorAdapter
{
	private static final String KEY_HEADER_PAY_AUDIT_USER = "pay_audit_user";
	private static final String KEY_HEADER_PAY_AUDIT_DOMAIN = "pay_audit_domain";
	private static final String KEY_HEADER_PAY_AUDIT_BUSINESS_UNIT = "pay_audit_business_unit";
	Logger log = LoggerFactory.getLogger(this.getClass());

	public boolean preHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException
	{
		String domain = request.getParameter(KEY_HEADER_PAY_AUDIT_DOMAIN);
		String businessUnit = request.getParameter(KEY_HEADER_PAY_AUDIT_BUSINESS_UNIT);
		String user = request.getParameter(KEY_HEADER_PAY_AUDIT_USER);

		String parameters[]=new String[4];
		parameters[0]=domain;
		parameters[1]=businessUnit;
		parameters[2]=user;
		parameters[3]=request.getQueryString();
		log.info("domain:{},businessUnit:{},user:{},queryString:{}",parameters);

		return true;
	}
} // end class
