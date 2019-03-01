package it.volpini.vgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import it.volpini.vgi.dao.UserLocationDao;
import it.volpini.vgi.domain.Legenda;
import it.volpini.vgi.domain.UserLocation;
import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.general.Esito;
import it.volpini.vgi.general.Result;
import it.volpini.vgi.utils.GeometryUtils;

@Service
@Transactional
public class UserLocationService {
	
	@Autowired
	private UserLocationDao userLocationDao;
	
	@Autowired
	private GeometryUtils geomUtils;
	
	public UserLocation saveOrUpdate(UserLocation userLocation) {
		return userLocationDao.save(userLocation);
	}
	
	public Optional<UserLocation> findById(Long id) {
		return userLocationDao.findById(id);
	}
	
	public List<UserLocation> findAll(){
		return userLocationDao.findAll();
	}
	
	public void delete(Long id) {
		userLocationDao.deleteById(id);
	}
	
	public List<UserLocation> findByIdUser(Long id){
		return userLocationDao.findByVgiUser_id(id);
	}
	
	public List<UserLocation> findByUserAndLegenda (Long idUser, Long idLegenda){
		return userLocationDao.findByVgiUser_idAndLegenda_id(idUser, idLegenda);
	}
	
	public Result<UserLocation> saveLocation(Optional<UserLocation> oplocation, Optional<Long> opIdUser, Long idLegenda) {
		Result<UserLocation> result = new Result<>();
		Esito esito;
		try {
		if (oplocation.isPresent()) {
			UserLocation location = oplocation.get();
			Legenda legenda = new Legenda();
			legenda.setId(idLegenda);
			Point point = geomUtils.getPoint(location.getLongitude(), location.getLatitude());
			point.setSRID(3857);
			location.setLegenda(legenda);
			location.setLocation(point);
			if (opIdUser.isPresent()) {
				location.setVgiUser(new VgiUser(opIdUser.get()));
				saveOrUpdate(location);
				esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
			} else {
				esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE
						+ " impossibile recuperare l'utente da associare alla posizione inviata");
			}
		} else {
			esito = new Esito(CostantiVgi.CODICE_ERRORE,
					CostantiVgi.DESCR_ERRORE + " impossibile recuperare la posizione inviata");
		}
		}catch (Throwable t) {
			t.printStackTrace();
			esito = new Esito(CostantiVgi.CODICE_ERRORE,
					CostantiVgi.DESCR_ERRORE +t.getMessage());
		}
		result.setEsito(esito);
		return result;
	}
	
	public Result<UserLocation> searchLocation(Optional<String> intervalloAnni, Optional<Long> idLegenda,
			Optional<Geometry> geom) {
		Result<UserLocation> result = new Result<>();
		Esito esito;
		try {
			Integer annoA = null;
			Integer annoB = null;
			if (intervalloAnni.isPresent()) {
				String[] anni = intervalloAnni.get().split("-");
				annoA = new Integer(anni[0]);
				annoB = new Integer(anni[1]);
			}
			List<UserLocation> locations = userLocationDao.searchLocations(Optional.ofNullable(annoA),
					Optional.ofNullable(annoB), idLegenda, geom);
			if (locations.size() > 0) {
				esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
				result.setResults(locations);
			} else {
				esito = new Esito(CostantiVgi.CODICE_OK_RESULT_NULL, CostantiVgi.DESCR_OK_RESULT_NULL);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + t.getMessage());
		}
		result.setEsito(esito);
		return result;
	}
	
	public Result<UserLocation> update(Optional<UserLocation> ul) {
		Result<UserLocation> result = new Result<>();
		Esito esito;
		try {
			if (ul.isPresent()) {
				result.setResult(saveOrUpdate(ul.get()));
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
	
	public Result<UserLocation> deleteLocation(Optional<Long> idLocation){
		Result<UserLocation> result=new Result<>();
		Esito esito;
		try {
		if(idLocation.isPresent()) {
			delete(idLocation.get());
			esito= new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
		}else {
			esito= new Esito(CostantiVgi.CODICE_OK_RESULT_NULL, CostantiVgi.DESCR_OK_RESULT_NULL+": nessuna localizzazione è stata passata per la cancellazione");
		}
		}catch(Throwable t) {
			t.printStackTrace();
			esito = new Esito(CostantiVgi.CODICE_ERRORE,
					CostantiVgi.DESCR_ERRORE +t.getMessage());
		}
		result.setEsito(esito);
		return result;
	}
	
	public Result<UserLocation> getUserLocations(Optional<Long> idUser) {
		Result<UserLocation> result = new Result<UserLocation>();
		Esito esito;
		try {
			if (idUser.isPresent()) {
				List<UserLocation> userlocations = findByIdUser(idUser.get());
				if (userlocations != null && userlocations.size() > 0) {
					result.setResults(userlocations);
					esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
				} else {
					esito = new Esito(CostantiVgi.CODICE_OK_RESULT_NULL, CostantiVgi.DESCR_OK_RESULT_NULL);
				}
			} else {
				esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + t.getMessage());
		}
		result.setEsito(esito);
		return result;
	}
	
	public Result<UserLocation> getUserLocationsByLegenda(Optional<Long> idUser, Optional<Long> idLegenda) {
		Result<UserLocation> result = new Result<UserLocation>();
		Esito esito;
		try {
			if (idUser.isPresent() && idLegenda.isPresent()) {
				List<UserLocation> userlocations = findByUserAndLegenda(idUser.get(), idLegenda.get());
				if (userlocations != null && userlocations.size() > 0) {
					result.setResults(userlocations);
					esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
				} else {
					esito = new Esito(CostantiVgi.CODICE_OK_RESULT_NULL, CostantiVgi.DESCR_OK_RESULT_NULL);
				}
			} else {
				esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + t.getMessage());
		}
		result.setEsito(esito);
		return result;
	}
	
	
	public Result<UserLocation> getUserLocationsById(Long idLocation) {
		Result<UserLocation> result = new Result<UserLocation>();
		Esito esito;
		try {
			Optional<UserLocation> location = findById(idLocation);
			if (location.isPresent()) {
				result.setResult(location.get());
				esito = new Esito(CostantiVgi.CODICE_OK, CostantiVgi.DESCR_OK);
			} else {
				esito = new Esito(CostantiVgi.CODICE_OK_RESULT_NULL, CostantiVgi.DESCR_OK_RESULT_NULL);
			}

		} catch (Throwable t) {
			t.printStackTrace();
			esito = new Esito(CostantiVgi.CODICE_ERRORE, CostantiVgi.DESCR_ERRORE + t.getMessage());
		}
		result.setEsito(esito);
		return result;
	}
	

}
