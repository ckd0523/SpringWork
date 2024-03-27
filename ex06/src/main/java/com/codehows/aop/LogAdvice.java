package com.codehows.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component
public class LogAdvice {

	@Before( "execution(* com.codehows.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("=========================");
	}
	
	@Before( "execution(* com.codehows.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	 public void logBeforeWithParam(String str1, String str2) {
		log.info("str1 : " +str1);
		log.info("str2 : " +str2);
	}
	
	@AfterThrowing(pointcut = "execution(* com.codehows.service.SampleService*.*(..))", throwing="exception")
	
	public void logExcption(Exception exception) {
		log.info("Exception......!!!!");
		log.info("exception : " +exception);
	}
	
	@Around("execution(* com.codehows.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		
		log.info("Target : " +pjp.getTarget());	//메서드 객체
		log.info("param : " +Arrays.toString(pjp.getArgs())); // 매서드 매개변수 확인
		
		// 리턴에 대한 초기화
		Object result = null;
		
		try {
			result = pjp.proceed();	// 매서드 실행 -> 결과값을 result 저장
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();	// 매서드 실행 후 시간 값 가져오기
		
		log.info("Time : " +(end-start));	// 매서드가 실행하는데 걸린 시간 구하기
		
		return result;
	}
	
}
