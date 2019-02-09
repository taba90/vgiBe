package it.volpini.vgi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.volpini.vgi.domain.RoleUser;

@Repository
public interface RoleUserDao extends JpaRepository<RoleUser,Long>{
	
	public RoleUser findByRoleName(String roleName);
	
	public List<RoleUser> findByUtenti_Username(String username);

}
