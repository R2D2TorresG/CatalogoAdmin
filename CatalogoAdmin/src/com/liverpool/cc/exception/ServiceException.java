package com.liverpool.cc.exception;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ServiceException extends RuntimeException {
	
	private static final Logger logger = LogManager.getLogger(ServiceException.class);

	private static final long serialVersionUID = 1L;
	
	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		logger.info(message, cause);
	}

	public ServiceException(String message) {
		super(message);
		logger.info(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
		logger.info(cause);
	}
	
	
	

}