package it.volpini.vgi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.volpini.vgi.domain.RoleUser;

@Repository
public interface RoleUserDao extends JpaRepository<RoleUser,Long>{
	
	public RoleUser findByRoleName(String roleName);

}
