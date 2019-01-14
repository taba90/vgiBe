package it.volpini.vgi.security;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.domain.RoleUser;
import it.volpini.vgi.service.VgiUserService;

@Service
public class VgiUserDetailsService implements UserDetailsService{

	@Autowired
	private VgiUserService vgiUserService;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<VgiUser> opUser = Optional.ofNullable(vgiUserService.findByUsername(username));
        opUser.orElseThrow(()->new UsernameNotFoundException("Nessun utente trovato con username "+username));
        return new org.springframework.security.core.userdetails.User(opUser.get().getUsername(), 
        		opUser.get().getPassword(), true, true, true, true, getGrantedAuthorities(opUser.get()));
            
    }

    private Set<GrantedAuthority> getGrantedAuthorities(VgiUser user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRuoli().forEach((RoleUser r)-> authorities.add(new SimpleGrantedAuthority(r.toString())));
        return authorities;
    }
}
