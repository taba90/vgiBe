package it.volpini.vgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.volpini.vgi.dao.IntervalloEtaDao;
import it.volpini.vgi.domain.IntervalloEta;
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.general.Esito;
import it.volpini.vgi.general.Result;

@Service
public class IntervalloEtaService {
	
	@Autowired
	private IntervalloEtaDao intervalloEtaDao;
	
	public IntervalloEta save(IntervalloEta intervalloEta) {
		return intervalloEtaDao.save(intervalloEta);
	}
	
	public Optional<IntervalloEta> findById(Long id){
		return intervalloEtaDao.findById(id);
	}
	
	public List<IntervalloEta> findAll(){
		return intervalloEtaDao.findAll();
	}
	
	public void delete(Long id) {
	    intervalloEtaDao.deleteById(id);
	}
	
	public void delete(IntervalloEta intervalloEta) {
	    intervalloEtaDao.delete(intervalloEta);
	}
	
	
	public Result<IntervalloEta> save(Optional<IntervalloEta> ie) {
		Result<IntervalloEta> result=new Result<>();
		Esito esito;
		try {
			if (ie.isPresent()) {
				result.setResult(save(ie.get()));
				esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
			} else {
				esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + " nessun input da recuperare");
			}
		} catch (Exception e) {
			esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + e.getMessage());
		}
		result.setEsito(esito);
		return result;
	}
	
	public Result<IntervalloEta> findAllIntervalli(){
		Result<IntervalloEta> result=new Result<>();
		Esito esito;
		try {
			result.setResults(findAll());
			esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
		}catch(Exception e) {
			esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + e.getMessage());
		}
		result.setEsito(esito);
		return result;

	}
	
	public Result<IntervalloEta> update(Optional<IntervalloEta> ie) {
		Result<IntervalloEta> result = new Result<>();
		Esito esito;
		try {
			if (ie.isPresent()) {
				result.setResult(save(ie.get()));
				esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
			} else {
				esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + "nessun input passato");
			}
		} catch (Exception e) {
			esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + e.getMessage());
		}
		result.setEsito(esito);
		return result;
	}
	
	public Result<IntervalloEta> findById(Optional<Long> id) {
		Result<IntervalloEta> result = new Result<>();
		Esito esito;
		try {
			if (id.isPresent()) {
				Optional<IntervalloEta> ie = findById(id.get());
				if (ie.isPresent()) {
					result.setResult(ie.get());
					esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
				} else {
					esito = new Esito(CostantiVgi.CODICE_OK_RESULT_NULL, CostantiVgi.DESCR_OK_RESULT_NULL);
				}
			} else {
				esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + "nessun input passato");
			}

		} catch (Exception e) {
			esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + e.getMessage());
		}
		result.setEsito(esito);
		return result;
	}
	
	public Result<IntervalloEta> delete (Optional<Long> id){
		Result<IntervalloEta> result=new Result<>();
		Esito esito;
		try {
			if(id.isPresent()) {
				delete(id.get());
				esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
			}else {
				esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE+"nessun input passato");
			}
			
		}catch(Exception e) {
			esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + e.getMessage());
		}
		result.setEsito(esito);
		return result;
	}

}
