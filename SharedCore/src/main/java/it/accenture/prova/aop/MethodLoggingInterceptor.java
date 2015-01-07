package it.accenture.prova.aop;

import java.lang.reflect.Method;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;


public class MethodLoggingInterceptor implements MethodBeforeAdvice,AfterReturningAdvice, ThrowsAdvice{

private static Logger logger = Logger.getLogger(MethodLoggingInterceptor.class);	
	
	public MethodLoggingInterceptor(){
	}
	
	private boolean printStackTrace = false;
	
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		String classMethodIdentifier = method.getDeclaringClass()
				.getSimpleName()
				+ " - " + method.getName();
		logger.debug(classMethodIdentifier + " - Inizio");
	}

	public void afterReturning(Object arg0, Method method, Object[] arg2,
			Object arg3) throws Throwable {
		String classMethodIdentifier = method.getDeclaringClass()
				.getSimpleName()
				+ " - " + method.getName();
		logger.debug(classMethodIdentifier + " - Fine");
	}

	public void afterThrowing(Method method, Object[] args, Object target, Throwable ex) 
	{ 
		String classMethodIdentifier = method.getDeclaringClass().getSimpleName()
		+ " - " + method.getName();
		logger.error(classMethodIdentifier + " - Eccezione: " + ex.toString());
		if (printStackTrace)
			logger.error(classMethodIdentifier + " - Eccezione stacktrace: " + ExceptionUtils.getFullStackTrace(ex));
	}

	public void setPrintStackTrace(boolean printStackTrace) {
		this.printStackTrace = printStackTrace;
	}
}
