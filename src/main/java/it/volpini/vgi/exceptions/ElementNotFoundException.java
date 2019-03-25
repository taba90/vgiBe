package it.volpini.vgi.exceptions;

public class ElementNotFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Throwable originalException;
	
	public ElementNotFoundException () {
		
	}
	
	public ElementNotFoundException (String message) {
		super(message);
	}
	
	public ElementNotFoundException (String message, Throwable t) {
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
