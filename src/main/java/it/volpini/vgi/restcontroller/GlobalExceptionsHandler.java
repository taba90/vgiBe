package it.volpini.vgi.restcontroller;


import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



import it.volpini.vgi.exceptions.ElementNotFoundException;
import it.volpini.vgi.exceptions.PointOutOfAreaException;
import it.volpini.vgi.exceptions.VgiAuthenticationException;
import it.volpini.vgi.general.Esito;


@RestControllerAdvice
@EnableWebMvc
public class GlobalExceptionsHandler {
	
	@ExceptionHandler(ElementNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Esito> handleElementNotFoundException (HttpServletResponse resp, ElementNotFoundException nfe){
		return new ResponseEntity<Esito> (new Esito(nfe.getMessage(), false), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(VgiAuthenticationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<Esito> handleVgiAuthenticationException (HttpServletResponse resp, VgiAuthenticationException vae){
		return new ResponseEntity<Esito> (new Esito(vae.getMessage(), false), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Esito> handleInternalServerError (HttpServletResponse resp, Exception ex){
		return new ResponseEntity<Esito> (new Esito(ex.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<Esito> handleConstraintViolationException (HttpServletResponse resp, ConstraintViolationException ex){
		return new ResponseEntity<Esito> (new Esito("L'operazione richiesta viola dei vincoli sull'elemento selezionato", false), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(PointOutOfAreaException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<Esito> handlePointOutOfAreaException (HttpServletResponse resp, PointOutOfAreaException ex){
		return new ResponseEntity<Esito> (new Esito(ex.getMessage(), false), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	

}
