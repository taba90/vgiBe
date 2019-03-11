package it.volpini.vgi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.volpini.vgi.dao.RoleUserDao;
import it.volpini.vgi.domain.RoleUser;
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.general.Esito;

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
	
	public List<RoleUser> findByUsername(String username){
		return roleDao.findByUtenti_Username(username);
	}
	
	public List<RoleUser> findByUserName(String username){
		return findByUsername(username);
	}

}
