package it.volpini.vgi.exceptions;

public class VgiAuthenticationException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Throwable originalException;
	
	public VgiAuthenticationException () {
		
	}
	
	public VgiAuthenticationException (String message) {
		super(message);
	}
	
	public VgiAuthenticationException (String message, Throwable t) {
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
