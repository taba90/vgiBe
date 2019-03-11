package it.volpini.vgi.security;

import java.io.IOException;
import java.util.Date;

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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import it.volpini.vgi.exceptions.VgiAuthenticationException;
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.general.Esito;
import it.volpini.vgi.security.LoginVgi;

@Service
@Transactional
public class AuthService {
	
	@Autowired
	private AuthenticationManager authMan;
	
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
        Algorithm algorithm = Algorithm.HMAC256(CostantiVgi.SECRET);
        String token = JWT.create()
                .withIssuer("login")
                .withClaim("subject", auth.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + (120 * 60 * 1000)))
                .sign(algorithm);
        res.addHeader("X-Vgi", token);
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
