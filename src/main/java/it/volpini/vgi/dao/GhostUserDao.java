package it.volpini.vgi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.volpini.vgi.domain.GhostUser;

@Repository
public interface GhostUserDao extends JpaRepository<GhostUser, Long>{
	
	@Query("select g.id from GhostUser g where g.username= :userId")
	public Optional<Long> findGhostUserIdByUsername(@Param("userId") String username);

}
