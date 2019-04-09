package it.volpini.vgi.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.volpini.vgi.domain.VgiUser;

@Repository
public interface VgiUserDao extends JpaRepository<VgiUser,Long>{
	
	public VgiUser findByUsernameIgnoreCase(String username);
	
	@Query(" Select u from VgiUser u where u.id not in ("
			+ "select u.id from VgiUser u join u.ruoli r where "
			+ "r.roleName = :roleName)")
	public Page<VgiUser> findAllNotRole (@Param("roleName")String roleName, Pageable pageable);
	
	@Query (" select count (u.id) from VgiUser u where u.id not in ("
			+ "select u.id from VgiUser u join u.ruoli r where "
			+ "r.roleName = :roleName)")
	public Long countNotRole(@Param("roleName") String roleName);
}
