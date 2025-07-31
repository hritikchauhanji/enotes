package com.enotes.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	@Around("execution(* com.enotes.controller..*(..))")
	public Object joinPointController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Signature signature = proceedingJoinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		log.info("Start Calling :: {} :: {}()", className, methodName);
		long start = System.currentTimeMillis();
		Object proceed = proceedingJoinPoint.proceed();
		long duration = System.currentTimeMillis() - start;
		log.info("End Calling :: {} :: {}() :: {} ms", className, methodName, duration);
		return proceed;
	}

	@Around("execution(* com.enotes.service..*(..))")
	public Object joinPointService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Signature signature = proceedingJoinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		log.info("Start Calling :: {} :: {}()", className, methodName);
		long start = System.currentTimeMillis();
		Object proceed = proceedingJoinPoint.proceed();
		long duration = System.currentTimeMillis() - start;
		log.info("End Calling :: {} :: {}() :: {} ms", className, methodName, duration);
		return proceed;
	}
}
