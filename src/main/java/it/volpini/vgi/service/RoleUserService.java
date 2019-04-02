package it.volpini.vgi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.volpini.vgi.dao.RoleUserDao;
import it.volpini.vgi.domain.RoleUser;

@Service
@Transactional(rollbackFor=Exception.class)
public class RoleUserService {
	
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
	
	public RoleUser saveRoleUser (RoleUser role) {
		return roleDao.save(role);
	}
	
	public List<RoleUser> findByRoleNameIn (List<String> roleNames){
		return roleDao.findByRoleNameIn(roleNames);
	}

}
