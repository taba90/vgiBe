package it.volpini.vgi.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.volpini.vgi.dao.custom.CustomUserLocationDao;
import it.volpini.vgi.domain.UserLocation;

@Repository
public interface UserLocationDao extends JpaRepository<UserLocation, Long>, CustomUserLocationDao{
	
	
	public List<UserLocation> findByVgiUser_id(Long id);
	
	public List<UserLocation> findByVgiUser_idAndLegenda_id(Long idUser, Long idLegenda);
	
	public Optional<UserLocation> findTop1ByLegenda_id(Long idLegenda);

}
