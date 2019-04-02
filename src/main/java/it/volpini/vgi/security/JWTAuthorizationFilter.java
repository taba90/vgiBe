/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.volpini.vgi.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.auth0.jwt.exceptions.TokenExpiredException;

import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.service.VgiUserService;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private VgiUserService userService;

	private JWTService tokenService;

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String token = req.getHeader(CostantiVgi.AUTH_TOKEN_KEY);
		if (token == null) {
			chain.doFilter(req, res);
		} else {
			try {
				if (tokenService == null) {
					ServletContext servletContext = req.getServletContext();
					WebApplicationContext webApplicationContext = WebApplicationContextUtils
							.getWebApplicationContext(servletContext);
					tokenService = webApplicationContext.getBean(JWTService.class);
				}
				String username = tokenService.verifyToken(token, CostantiVgi.LONGIN_ISSUE, CostantiVgi.CLAIM_SUBJECT);
				res.addHeader(CostantiVgi.AUTH_TOKEN_KEY, token);
				UsernamePasswordAuthenticationToken authentication = getAuthentication(username, req);
				if(authentication!=null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
					chain.doFilter(req, res);
				}else {
					res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente non possiede le autorizzazioni necessarie ad eseguire l'operazione");
				}
			} catch (TokenExpiredException tee) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN, "Sessione scaduta rieseguire la login");
			}
		}
	}

	@SuppressWarnings("null")
	private UsernamePasswordAuthenticationToken getAuthentication(String username, HttpServletRequest req)
			throws IllegalArgumentException, UnsupportedEncodingException {
		UsernamePasswordAuthenticationToken authtoken = null;
		if (userService == null) {
			ServletContext servletContext = req.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			userService = webApplicationContext.getBean(VgiUserService.class);
		}
		VgiUser u = userService.findByUsername(username);

		Set<GrantedAuthority> authorities = new HashSet<>();
		if (u.getRuoli() != null && u.getRuoli().size()>0) {
			u.getRuoli().forEach(r -> {
				authorities.add(new SimpleGrantedAuthority(r.toString()));
			});
			authtoken = new UsernamePasswordAuthenticationToken(u.getUsername(), null, authorities);
			return authtoken;
		} else {
			return null;
		}
	}

}
