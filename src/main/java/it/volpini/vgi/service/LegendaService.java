package it.volpini.vgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.volpini.vgi.dao.LegendaDao;
import it.volpini.vgi.domain.Legenda;
import it.volpini.vgi.exceptions.ElementNotFoundException;
import it.volpini.vgi.general.Esito;

@Service
@Transactional(rollbackFor=Exception.class)
public class LegendaService {

	@Autowired
	private LegendaDao legendaDao;

	public Legenda save(Legenda legenda) {
		return legendaDao.save(legenda);
	}

	public Optional<Legenda> findById(Long id) {
		return legendaDao.findById(id);
	}

	public List<Legenda> findAll() {
		return legendaDao.findAll(new Sort(Sort.Direction.ASC, "codice"));
	}

	public Esito delete(Long id) {
		legendaDao.deleteById(id);
		return new Esito("Operazione eseguita senza errori", true);
	}

	public Legenda saveOrUpdate(Legenda legenda) {
		return save(legenda);
	}

	public Legenda findLegendaById(Long id) throws ElementNotFoundException {
		return findById(id)
				.orElseThrow(() -> new ElementNotFoundException("Errore, l'elemento indicato non è presente"));
	}

}
