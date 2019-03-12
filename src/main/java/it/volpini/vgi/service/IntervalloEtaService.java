package it.volpini.vgi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.volpini.vgi.dao.IntervalloEtaDao;
import it.volpini.vgi.domain.IntervalloEta;
import it.volpini.vgi.exceptions.ElementNotFoundException;
import it.volpini.vgi.exceptions.LinkedElementsExistException;
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.general.Esito;


@Service
@Transactional
public class IntervalloEtaService {
	
	@Autowired
	private IntervalloEtaDao intervalloEtaDao;
	
	public IntervalloEta saveOrUpdate(IntervalloEta intervalloEta){
		return intervalloEtaDao.save(intervalloEta);
	}
	
	public IntervalloEta findById(Long id) throws ElementNotFoundException{
		return intervalloEtaDao.findById(id).orElseThrow(()-> new ElementNotFoundException("L'elemento indicato non è presente"));
	}
	
	public List<IntervalloEta> findAll(){
		return intervalloEtaDao.findAll();
	}
	
	public void delete(Long id) throws LinkedElementsExistException {
		try {
			intervalloEtaDao.deleteById(id);
		} catch (DataIntegrityViolationException dive) {
			throw new LinkedElementsExistException(dive.getMessage(), dive);

		}
	}
	
	public Esito deleteIntervallo(Long id) throws LinkedElementsExistException {
		delete(id);
		return new Esito(CostantiVgi.DESCR_OK, true);
	}
	
	public void delete(IntervalloEta intervalloEta) {
	    intervalloEtaDao.delete(intervalloEta);
	}
	
	
	
	
	
	
	
	
	
	

}
