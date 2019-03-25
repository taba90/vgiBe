package it.volpini.vgi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.volpini.vgi.domain.CheckGeometry;

@Repository
public interface CheckGeometryDao extends JpaRepository<CheckGeometry, Long> {
	
	@Query(value="SELECT * FROM CHECK_GEOMETRY LIMIT 1", nativeQuery=true)
	public Optional<CheckGeometry> findTop1();
	
	

}
