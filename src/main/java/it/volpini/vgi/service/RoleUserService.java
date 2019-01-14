package it.volpini.vgi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.volpini.vgi.dao.RoleUserDao;
import it.volpini.vgi.domain.RoleUser;

@Service
@Transactional
public class RoleUserService {
	
	public static final String ROLE_USER="ROLE_USER";
	
	public static final String ROLE_ADMIN="ROLE_ADMIN";
	
	@Autowired
	private RoleUserDao roleDao;
	
	public RoleUser findByRoleName(String roleName) {
		return roleDao.findByRoleName(roleName);
	}

}
