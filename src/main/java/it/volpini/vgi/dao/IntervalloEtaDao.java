package it.volpini.vgi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.volpini.vgi.domain.IntervalloEta;

@Repository
public interface IntervalloEtaDao extends JpaRepository<IntervalloEta, Long>{
	
	public Optional<IntervalloEta> findByIntervallo(String intervallo);

}
