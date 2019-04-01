package it.volpini.vgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import it.volpini.vgi.dao.CheckGeometryDao;
import it.volpini.vgi.dao.UserLocationDao;
import it.volpini.vgi.domain.CheckGeometry;
import it.volpini.vgi.domain.UserLocation;
import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.exceptions.ElementNotFoundException;
import it.volpini.vgi.exceptions.PointOutOfAreaException;
import it.volpini.vgi.general.CostantiVgi;
import it.volpini.vgi.general.Esito;
import it.volpini.vgi.utils.GeometryUtils;

@Service
@Transactional
public class UserLocationService {
	
	@Autowired
	private UserLocationDao userLocationDao;
	
	@Autowired
	private CheckGeometryDao checkGeomDao;
	
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
	
	public Esito saveOrUpdateLocation(UserLocation location, Long idUser)
			throws ElementNotFoundException {
		Point point = geomUtils.getPoint(location.getLongitude(), location.getLatitude());
		point.setSRID(3857);
		CheckGeometry polygon = checkGeomDao.findTop1().orElseThrow(
				() -> new ElementNotFoundException("Errore: la geometria di validazione non è disponibile"));
		if (!polygon.getGeometry().contains(point)) {
			return new Esito("Impossibile aggiungere un punto al di fuori dell'area definita per questa versione dell'applicativo", false);
		} else if (!canAddPointsToLegenda(idUser, location.getLegenda().getId())) {
			return new Esito("Il numero massimo di punti per questo campo legenda è stato già raggiunto", false);
		}
		else {
			location.setLocation(point);
			location.setVgiUser(new VgiUser(idUser));
			saveOrUpdate(location);
			return new Esito(CostantiVgi.DESCR_OK, true);
		}
	}
	
	public List<UserLocation> searchLocation(Optional<Integer> annoA, Optional<Integer> annoB, Optional<Long> idLegenda,
			Optional<Geometry> geom) {
			List<UserLocation> locations = userLocationDao.searchLocations(annoA,
					annoB, idLegenda, geom);
			return  locations;
	}
	
	public Esito deleteLocation(Long idLocation) { 
		delete(idLocation);
		return new Esito("Operazione eseguita senza errori", true);
	}
	
	public List<UserLocation> getUserLocations(Long idUser) {
		
		return findByIdUser(idUser);
	}
	
	public List<UserLocation> getUserLocationsByLegenda(Long idUser, Long idLegenda) {
		
		return findByUserAndLegenda(idUser, idLegenda);
	}
	
	
	public UserLocation getUserLocationById(Long idLocation) throws ElementNotFoundException {
		
		return findById(idLocation).orElseThrow(()->new ElementNotFoundException("L'elemento indicato non esiste"));
	}
	
	public boolean canAddPointsToLegenda (Long idUser, Long idLegenda) {
		if(userLocationDao.countByVgiUser_idAndLegenda_id(idUser, idLegenda).intValue()
				>= CostantiVgi.max_point_legenda.intValue()) {
			return false;
		}else {
			return true;
		}
	}
	

}
