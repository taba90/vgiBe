package it.volpini.vgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.volpini.vgi.dao.LegendaDao;
import it.volpini.vgi.domain.Legenda;
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.general.Esito;
import it.volpini.vgi.general.Result;

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
	
	public void delete(Long id) {
		legendaDao.deleteById(id);
	}
	
	public Result<Legenda> saveOrUpdate(Optional<Legenda> legenda) {
		Result<Legenda> result = new Result<>();
		Esito esito;
		try {
			if (legenda.isPresent()) {
				save(legenda.get());
				esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
			} else {
				esito = new Esito(CostantiVgi.CODICE_OK_RESULT_NULL,
						CostantiVgi.DESCR_OK_RESULT_NULL + ": nessuna voce di legenda passata per il salvataggio");
			}
		} catch (Throwable t) {
			esito=new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE+t.getMessage());
		}
		result.setEsito(esito);
		return result;
	}
	
	public Result<Legenda> findAllLegenda(){
		Result<Legenda> result=new Result<>();
		Esito esito;
		try {
			List<Legenda> legende=findAll();
			if(legende.size()>0) {
				result.setResults(legende);
				esito=new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
			}else {
				esito=new Esito(CostantiVgi.CODICE_OK_RESULT_NULL, CostantiVgi.DESCR_OK_RESULT_NULL);
			}
			
		}catch(Throwable t) {
			esito=new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE+": errore durante la ricerca delle voci di legenda");
		}
		result.setEsito(esito);
		return result;
	}
	
	public Result<Legenda> findById(Optional<Long> id) {
		Result<Legenda> result = new Result<>();
		Esito esito;
		try {
			if (id.isPresent()) {
				Optional<Legenda> leg = findById(id.get());
				if (leg.isPresent()) {
					result.setResult(leg.get());
					esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
				} else {
					esito = new Esito(CostantiVgi.CODICE_OK_RESULT_NULL, CostantiVgi.DESCR_OK_RESULT_NULL);
				}
			} else {
				esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + "nessun input passato");
			}

		} catch (Throwable t) {
			esito = new Esito(CostantiVgi.CODICE_ERRORE,
					CostantiVgi.DESCR_ERRORE + ": errore durante la ricerca delle voci di legenda");
		}
		result.setEsito(esito);
		return result;
	}

}
