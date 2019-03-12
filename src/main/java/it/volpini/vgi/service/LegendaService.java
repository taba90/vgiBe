package it.volpini.vgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.volpini.vgi.dao.LegendaDao;
import it.volpini.vgi.domain.Legenda;
import it.volpini.vgi.exceptions.ElementNotFoundException;
import it.volpini.vgi.exceptions.LinkedElementsExistException;
import it.volpini.vgi.general.Esito;

@Service
@Transactional
public class LegendaService {
	
	@Autowired
	private LegendaDao legendaDao;
	
	public Legenda save(Legenda legenda) {
		return legendaDao.save(legenda);
	}
	
	public Optional<Legenda> findById(Long id){
		return legendaDao.findById(id);
	}
	
	public List<Legenda> findAll(){
		return legendaDao.findAll(new Sort(Sort.Direction.ASC, "codice"));
	}
	
	public Esito delete(Long id) throws LinkedElementsExistException {
		try {
			legendaDao.deleteById(id);
			return new Esito("Operazione eseguita senza errori", true);
		} catch (DataIntegrityViolationException dive) {
			throw new LinkedElementsExistException(dive.getMessage(), dive);
		}
	}
	
	public Legenda saveOrUpdate(Legenda legenda){
		return save(legenda);
	}
	
	public Legenda findLegendaById(Long id) throws ElementNotFoundException{
		return findById(id)
				.orElseThrow(
						()-> new ElementNotFoundException("Errore, l'elemento indicato non è presente"));
	}

}
