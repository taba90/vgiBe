package it.volpini.vgi.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.volpini.vgi.exceptions.VgiAuthenticationException;
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.general.Esito;

@Service
@Transactional
public class AuthService {
	
	@Autowired
	private AuthenticationManager authMan;
	
	@Autowired
	private JWTService tokenService;
	
	private Authentication attemptAuthentication (LoginVgi login) {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                login.getUsername(), login.getPassword());
        
        Authentication authentication = authMan.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
		
	}
	
	private void successfullAuthentication (HttpServletRequest req,
            HttpServletResponse res,
            Authentication auth) throws IOException, ServletException {
		String token = tokenService.createToken(auth.getName(), CostantiVgi.CLAIM_SUBJECT, CostantiVgi.LONGIN_ISSUE);
		res.addHeader(CostantiVgi.AUTH_TOKEN_KEY, token);
        
    }
	
	public Esito authenticateUser(LoginVgi login, HttpServletRequest req, HttpServletResponse resp)
			throws VgiAuthenticationException {
		try {
			Authentication auth = attemptAuthentication(login);
			if (auth != null && auth.isAuthenticated()) {
				successfullAuthentication(req, resp, auth);
				return new Esito("Autenticazione eseguita con successo", true);
			} else {
				throw new VgiAuthenticationException("Autenticazione fallita");
			}
		} catch (IOException ioEx) {
			throw new VgiAuthenticationException(ioEx.getMessage());
		} catch (ServletException sEx) {
			throw new VgiAuthenticationException(sEx.getMessage());
		} catch (AuthenticationException authEx) {
			throw new VgiAuthenticationException(authEx.getMessage());
		}
	}

}
