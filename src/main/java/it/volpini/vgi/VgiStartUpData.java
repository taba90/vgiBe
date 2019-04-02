package it.volpini.vgi;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
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
	
	public void insertOnAppReady(ApplicationReadyEvent ready) {
		
		List<RoleUser> roles = roleService.findByRoleNameIn(asList(RoleUserDao.ROLE_USER, RoleUserDao.ROLE_ADMIN));
		if(roles==null || roles.size()<=0) {
			roleService.saveRoleUser(new RoleUser(RoleUserDao.ROLE_USER));
			roleService.saveRoleUser(new RoleUser(RoleUserDao.ROLE_ADMIN));
		}
		VgiUser user = userService.findByUsername("admin");
		if(user == null) {
		    user = new VgiUser("admin", "Password2019");
			userService.save(user);
		}
	}
}
