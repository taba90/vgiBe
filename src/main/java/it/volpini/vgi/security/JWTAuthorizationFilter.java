/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.volpini.vgi.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.service.VgiUserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    
    @Autowired
    private VgiUserService userService;

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        if (req.getHeader("X-Vgi") == null) {
            chain.doFilter(req, res);
        } else {
        	try {
            Optional<String> optoken = Optional.ofNullable(req.getHeader("X-Vgi"));
            UsernamePasswordAuthenticationToken authentication = getAuthentication(optoken, req);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        	}catch(TokenExpiredException tee) {
        		res.sendError(HttpServletResponse.SC_FORBIDDEN, "Sessione scaduta rieseguire la login");
        	}
        }
    }

    @SuppressWarnings("null")
    private UsernamePasswordAuthenticationToken getAuthentication(Optional<String> optoken, HttpServletRequest req) throws IllegalArgumentException, UnsupportedEncodingException {
        UsernamePasswordAuthenticationToken authtoken=null;
    	if (userService == null) {
            ServletContext servletContext = req.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userService = webApplicationContext.getBean(VgiUserService.class);
        }
    	Optional<DecodedJWT> optJwt=optoken.map(t->JWT.require(Algorithm.HMAC256(CostantiVgi.SECRET)).withIssuer(CostantiVgi.LONGIN_ISSUE).build().verify(t));
    	Optional<VgiUser> opU=optJwt.map(jwt->userService.findByUsername(jwt.getClaim("subject").asString()));
        if(opU.isPresent()) {
        	VgiUser u = opU.get();
        	Set<GrantedAuthority> authorities = new HashSet<>();
        	u.getRuoli().forEach(r->{
                authorities.add(new SimpleGrantedAuthority(r.toString()));});
                authtoken = new UsernamePasswordAuthenticationToken(opU.get().getUsername(), null, authorities);
        }
        return authtoken;
    }
}
