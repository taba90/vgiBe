package it.volpini.vgi.dao;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.volpini.vgi.domain.VgiUser;

@Repository
public interface VgiUserDao extends JpaRepository<VgiUser,Long>{
	
	public VgiUser findByUsernameIgnoreCase(String username);
	
	public Page<VgiUser> findAll (Pageable pageable);

}
