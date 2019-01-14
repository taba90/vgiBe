package it.volpini.vgi.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.volpini.vgi.domain.VgiUser;

@Repository
public interface VgiUserDao extends JpaRepository<VgiUser,Long>{
	
	public VgiUser findByUsernameIgnoreCase(String username);

}
