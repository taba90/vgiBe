package it.volpini.vgi.restcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.exceptions.ElementNotFoundException;
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
	
	@GetMapping("/sendResetMail")
	public Esito resetPasswordMail (@RequestParam("email") String email) throws ElementNotFoundException {
		return userService.sendResetPasswordEmail(email);
	}
	
	@PatchMapping("/resetPassword")
	public VgiUser resetPassword (@RequestParam("t") String token, @RequestBody LoginVgi login) throws ElementNotFoundException {
		return userService.resetPasswordUser(token, login.getPassword());
	}

}
