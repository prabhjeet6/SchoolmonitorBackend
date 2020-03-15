package com.schoolmonitor.exception;

/**
 * @author PrabhjeetS
 * @version 1.0
 */
public class GenericExceptionContainer<T> {
	public GenericExceptionContainer(T exceptionObject) {
		super();
		this.exceptionObject = exceptionObject;
	}

	@Override
	public String toString() {

		Class<?> exceptionClass = exceptionObject.getClass();
		if (exceptionObject instanceof SchoolMonitorException) {
			try {
				return exceptionClass.getDeclaredFields()[1].get((T) exceptionObject).toString();
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return exceptionClass.getName();
	}

	private T exceptionObject;

	public T getExceptionObject() {
		return exceptionObject;
	}

	public void setExceptionObject(T exceptionObject) {
		this.exceptionObject = exceptionObject;

	}

}
