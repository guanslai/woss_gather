package com.briup.util;

import java.util.Properties;


public class LoggerImpl implements Logger{
	
	private static org.apache.log4j.Logger logger;
	
	static {
		  logger = org.apache.log4j.Logger.getRootLogger();
	}

	@Override
	public void init(Properties properties) {
	}

	@Override
	public void debug(String str) {
		logger.debug(str);
	}

	@Override
	public void error(String str) {
		logger.error(str);
	}

	@Override
	public void fatal(String str) {
		logger.fatal(str);
	}

	@Override
	public void info(String str) {
		logger.info(str);
	}

	@Override
	public void warn(String str) {
		logger.warn(str);
	}

}
