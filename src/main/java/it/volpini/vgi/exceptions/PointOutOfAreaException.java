package it.volpini.vgi.exceptions;

public class PointOutOfAreaException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Throwable originalException;
	
	public PointOutOfAreaException () {
		
	}
	
	public  PointOutOfAreaException (String message) {
		super(message);
	}
	
	public  PointOutOfAreaException (String message, Throwable t) {
		super (message);
		this.originalException=t;
	}

	public Throwable getOriginalException() {
		return originalException;
	}

	public void setOriginalException(Throwable originalException) {
		this.originalException = originalException;
	}
}
