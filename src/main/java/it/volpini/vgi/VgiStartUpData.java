package it.volpini.vgi;

import static java.util.Arrays.asList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import it.volpini.vgi.dao.RoleUserDao;
import it.volpini.vgi.domain.RoleUser;
import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.service.RoleUserService;
import it.volpini.vgi.service.VgiUserService;

@Component
public class VgiStartUpData {

	@Autowired
	private VgiUserService userService;
	
	@Autowired
	private RoleUserService roleService;
	
	@Autowired
	private Environment env;
	
	@EventListener
	@Transactional
	public void insertOnAppReady(ApplicationReadyEvent ready) {
		
		List<RoleUser> roles = roleService.findByRoleNameIn(asList(RoleUserDao.ROLE_USER, RoleUserDao.ROLE_ADMIN));
		if(roles==null || roles.size()<=0) {
			roleService.saveRoleUser(new RoleUser(RoleUserDao.ROLE_USER));
			roleService.saveRoleUser(new RoleUser(RoleUserDao.ROLE_ADMIN));
		}
		VgiUser user = userService.findByUsername(env.getProperty("vgi.admin.username"));
		if(user == null) {
		    user = new VgiUser(env.getProperty("vgi.admin.username"), env.getProperty("vgi.admin.password"));
		    user.setEmail(env.getProperty("vgi.admin.email"));
		    user.setAnni(new Integer(env.getProperty("vgi.admin.anni")));
		    user.setRuoli(roleService.findAll());
			userService.saveUser(user);
		}
	}
}
