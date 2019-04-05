package it.volpini.vgi.restcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.exceptions.VgiAuthenticationException;
import it.volpini.vgi.general.Esito;
import it.volpini.vgi.security.AuthService;
import it.volpini.vgi.security.LoginVgi;
import it.volpini.vgi.service.VgiUserService;

@RestController
@CrossOrigin("*")
public class LoginRestController {
	
	
	@Autowired
	private VgiUserService userService;
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public Esito registerUser(@RequestBody @Valid VgiUser user) {
		return userService.saveUser(user);
	}
	
	@PostMapping("/login")
	public Esito login (@RequestBody LoginVgi loginVgi, HttpServletRequest req, HttpServletResponse resp) throws VgiAuthenticationException{
		return authService.authenticateUser(loginVgi, req, resp);
	}

}
