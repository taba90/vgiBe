package it.volpini.vgi.restcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.volpini.vgi.domain.RoleUser;
import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.exceptions.ElementNotFoundException;
import it.volpini.vgi.exceptions.VgiAuthenticationException;
import it.volpini.vgi.general.Esito;
import it.volpini.vgi.security.AuthService;
import it.volpini.vgi.security.LoginVgi;
import it.volpini.vgi.service.RoleUserService;
import it.volpini.vgi.service.VgiUserService;

@RestController
@CrossOrigin("*")
public class VgiUserRestController {

	@Autowired
	private VgiUserService userService;
	
	@Autowired
	private RoleUserService roleService;
	
	@Autowired
	private AuthService authService;
	
	
	@PostMapping("/register")
	public Esito registerUser(@RequestBody @Valid VgiUser user) {
		return userService.saveUser(user);
	}
	
	@DeleteMapping("/self")
	public Esito deleteSelfUser() throws ElementNotFoundException{
		return userService.selfDeleteUser();
	}
	
	@GetMapping("/self")
	public VgiUser getSelfUser() throws ElementNotFoundException{
		Long idAuth = userService.getIdAuthenticatedUser();
		return userService.getSelf(idAuth);
	}
	
	@PatchMapping("/update")
	public Esito update(@RequestBody @Valid VgiUser user) {
		return userService.saveUser(user);
	}
	
	@GetMapping("/roles")
	public List<RoleUser> getRuoliUtente(Authentication auth){
		return roleService.findByUserName(auth.getName());
	}
	
	@PostMapping("/login")
	public Esito login (@RequestBody LoginVgi loginVgi, HttpServletRequest req, HttpServletResponse resp) throws VgiAuthenticationException{
		return authService.authenticateUser(loginVgi, req, resp);
	}
	
	@GetMapping("/all")
	public List<VgiUser> getUtenti(){
		return userService.findAll();
	}
}

