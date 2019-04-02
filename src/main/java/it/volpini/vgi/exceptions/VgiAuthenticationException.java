package it.volpini.vgi.exceptions;

import org.springframework.security.core.AuthenticationException;

public class VgiAuthenticationException extends AuthenticationException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Throwable originalException;
	
	public VgiAuthenticationException () {
		super("Exception");
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
