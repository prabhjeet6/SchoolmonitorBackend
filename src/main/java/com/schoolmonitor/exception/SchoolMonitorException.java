package com.schoolmonitor.exception;
/**
 * @author PrabhjeetS
 * @version 1.0
 */
public class SchoolMonitorException extends RuntimeException {

	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(StackTraceElement[] stackTrace) {
		this.stackTrace = stackTrace;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	private static final long serialVersionUID = 1L;
	Throwable ex;
    StackTraceElement[] stackTrace;
    Throwable cause;
    
	public SchoolMonitorException(Throwable ex) {
		super();
		this.ex = ex;
		stackTrace=ex.getStackTrace();
		cause=ex.getCause();
		
	}
	

	public Throwable getEx() {
		return ex;
	}

	public void setEx(Throwable ex) {
		this.ex = ex;
	}
}
