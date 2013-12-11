package biz.janux.geography.aop;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;

/**
 * This class is an advice that throws a {@link RuntimeException} after a method executes.
 * This is useful for advising transactional methods when testing them. 
 * @author   <a href="mailto:moradaniel@janux.org">Daniel Mora</a>
 */
public class RuntimeExceptionAfterReturningAdvice implements AfterReturningAdvice {

	protected Log log = LogFactory.getLog(this.getClass());
	
	public void afterReturning(Object returnValue, Method method, Object[] args,Object target) throws Throwable {
		if (log.isDebugEnabled())
			log.debug("Throwing RuntimeException after method: " + method.getName());
		throw new RuntimeException();
		}
	}