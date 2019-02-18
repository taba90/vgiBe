package it.volpini.vgi.security;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.general.CostantiVgi;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
    private Optional<AuthenticationManager> opAuthMan;
	
    @Autowired
	public JWTAuthenticationFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        this.opAuthMan = Optional.ofNullable(authManager);
    }
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            opAuthMan.orElseGet(()-> WebApplicationContextUtils.getWebApplicationContext(
            		req.getServletContext()).getBean(AuthenticationManager.class));
            VgiUser user = new ObjectMapper().readValue(req.getInputStream(), VgiUser.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword());

            Authentication authentication = opAuthMan.get().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return authentication;

        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        Algorithm algorithm = Algorithm.HMAC256(CostantiVgi.SECRET);
        String token = JWT.create()
                .withIssuer("login")
                .withClaim("subject", auth.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + (120 * 60 * 1000)))
                .sign(algorithm);
        res.addHeader("X-Vgi", token);
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
	
	

}
