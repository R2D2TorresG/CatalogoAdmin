package com.liverpool.cc.util;

import org.apache.log4j.Logger;

import com.liverpool.cc.exception.ServiceException;

public class GeneraObjectBean  {

	private static final Logger logger = Logger.getLogger(GeneraObjectBean.class);
	
	private Object objectClase = null;
	private Class<?> className = null;

	public Object getObjectClase() {
		return objectClase;
	}

	public void setObjectClase(Object objectClase) {
		this.objectClase = objectClase;
	}

	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}

	public GeneraObjectBean(String claseNombre) {
		super();
		logger.debug("GeneraObjectBean" + ":" + claseNombre );
		try {
			className = Class.forName(claseNombre);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("Error", e);
		}
		try {
			objectClase = className.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("Error", e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("Error", e);
			
		}
	}

}
