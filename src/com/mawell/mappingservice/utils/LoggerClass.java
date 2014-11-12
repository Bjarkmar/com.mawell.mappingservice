package com.mawell.mappingservice.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerClass {
	static final Logger logger = LogManager.getLogger(LoggerClass.class.getName());
	public LoggerClass() {
		logger.trace("Entering Log4j Example.");
		logger.trace("Exiting Log4j Example.");
	}
}