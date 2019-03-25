package it.volpini.vgi.exceptions;


import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import it.volpini.vgi.general.Esito;


@RestControllerAdvice
public class GlobalExceptionsHandler {
	
	
	@ExceptionHandler({ElementNotFoundException.class, VgiAuthenticationException.class, ConstraintViolationException.class, PointOutOfAreaException.class, Exception.class})
	public final ResponseEntity<Esito> handleException(Exception ex){
		
		if(ex instanceof ElementNotFoundException) {
			return handleElementNotFoundException((ElementNotFoundException)ex);
		}else if (ex instanceof VgiAuthenticationException){
			return handleVgiAuthenticationException((VgiAuthenticationException)ex);
		}else if(ex instanceof ConstraintViolationException){
			return handleConstraintViolationException((ConstraintViolationException) ex);
		}else if(ex instanceof PointOutOfAreaException){
			return handlePointOutOfAreaException((PointOutOfAreaException) ex);
		}else {
			return handleInternalServerError(ex);
		}
		
		
		
	}
	
	protected ResponseEntity<Esito> handleElementNotFoundException (ElementNotFoundException nfe){
		return new ResponseEntity<Esito> (new Esito(nfe.getMessage(), false), HttpStatus.NOT_FOUND);
	}
	
	protected ResponseEntity<Esito> handleVgiAuthenticationException (VgiAuthenticationException vae){
		return new ResponseEntity<Esito> (new Esito(vae.getMessage(), false), HttpStatus.FORBIDDEN);
	}
	
	protected ResponseEntity<Esito> handleInternalServerError (Exception ex){
		return new ResponseEntity<Esito> (new Esito(ex.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	protected ResponseEntity<Esito> handleConstraintViolationException (ConstraintViolationException ex){
		return new ResponseEntity<Esito> (new Esito("Impossibile effetuare l'operazione sull'elemento", false), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	protected ResponseEntity<Esito> handlePointOutOfAreaException (PointOutOfAreaException ex){
		return new ResponseEntity<Esito> (new Esito(ex.getMessage(), false), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	

}
