package it.volpini.vgi.restcontroller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.volpini.vgi.security.AuthService;
import it.volpini.vgi.service.RoleUserService;
import it.volpini.vgi.service.VgiUserService;
import it.volpini.vgi.domain.RoleUser;
import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.general.Result;
import it.volpini.vgi.security.LoginVgi;

@RestController
@CrossOrigin("*")
public class VgiUserRestController {

	@Autowired
	private VgiUserService userService;
	
	@Autowired
	private RoleUserService roleService;
	
	@Autowired
	private AuthService authService;
	
	@GetMapping("/model")
	public VgiUser model(){
		
		return new VgiUser();
	}
	
	
	@PostMapping("/register")
	public Result<VgiUser> registerUser(@RequestBody VgiUser user){
		Optional<VgiUser> opuser=Optional.ofNullable(user);
		return userService.saveUser(opuser);
	}
	
	@DeleteMapping("/self")
	public Result<VgiUser> deleteUser(){
		return userService.selfDeleteUser();
	}
	
	@PatchMapping("/update")
	public Result<VgiUser> update(@RequestBody VgiUser user){
		Optional<VgiUser> opuser=Optional.ofNullable(user);
		return userService.saveUser(opuser);
	}
	
	@GetMapping("/roles")
	public Result<RoleUser> getRuoliUtente(Authentication auth){
		return roleService.findByUserName(auth.getName());
	}
	
	@PostMapping("/login")
	public ResponseEntity<Result<Boolean>> login (@RequestBody LoginVgi loginVgi, HttpServletRequest req, HttpServletResponse resp){
		Result<Boolean> result = authService.authenticateUser(loginVgi, req, resp);
		if(result.getResult().booleanValue()==true) {
			return new ResponseEntity<Result<Boolean>>(result, HttpStatus.OK);
		}else {
			return new ResponseEntity<Result<Boolean>>(result, HttpStatus.UNAUTHORIZED);
		}
	}
}
