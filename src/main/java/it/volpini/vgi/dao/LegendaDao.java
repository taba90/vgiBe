package it.volpini.vgi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.volpini.vgi.domain.Legenda;

@Repository
public interface LegendaDao extends JpaRepository<Legenda, Long>{
	

}
