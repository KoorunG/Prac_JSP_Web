package jspbook.ch14;

// LogTest 예시

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

	public static void main(String[] args) {
		String msg = "Log4j Log Test~";
		Logger logger = LoggerFactory.getLogger(LogTest.class);
		// 로그 레벨에 따른 로그 처리 메서드를 살펴보면 logger.info() 메서드의 경우,
	    // INFO 레벨에서 런타임 시 관심 있는 이벤트를 상태 콘솔에 출력하는 로그로 활용합니다.	    
		logger.info("Log4j Test Log~~~");
		
		
		// logger.warn() 메서드의 경우, WARN 레벨에서 오류는 아니지만 주의를 요구하는 경우에,
	    // 상태 콘솔에 출력하는 로그로 활용합니다.
		logger.warn("Log4j Test Log :  {}",msg);
	}
}
