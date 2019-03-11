package it.volpini.vgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import it.volpini.vgi.dao.UserLocationDao;
import it.volpini.vgi.domain.Legenda;
import it.volpini.vgi.domain.UserLocation;
import it.volpini.vgi.domain.VgiUser;
import it.volpini.vgi.exceptions.LinkedElementsExistException;
import it.volpini.vgi.exceptions.ElementNotFoundException;
import it.volpini.vgi.exceptions.NullParamException;
import it.volpini.vgi.exceptions.UserNotInSessionException;
import it.volpini.vgi.general.Esito;
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
	
	public void delete(Long id) throws LinkedElementsExistException {
		try {
			userLocationDao.deleteById(id);
		} catch (DataIntegrityViolationException dive) {
			throw new LinkedElementsExistException(dive.getMessage(), dive);
		}
	}
	
	public List<UserLocation> findByIdUser(Long id){
		return userLocationDao.findByVgiUser_id(id);
	}
	
	public List<UserLocation> findByUserAndLegenda (Long idUser, Long idLegenda){
		return userLocationDao.findByVgiUser_idAndLegenda_id(idUser, idLegenda);
	}
	
	public UserLocation saveLocation(Optional<UserLocation> oplocation, Optional<Long> opIdUser, Long idLegenda) throws UserNotInSessionException, NullParamException {
		UserLocation location = oplocation.orElseThrow(()->new NullParamException("Uno o più parametri non sono presenti nella request"));
		Legenda legenda = new Legenda();
		legenda.setId(idLegenda);
		Point point = geomUtils.getPoint(location.getLongitude(), location.getLatitude());
		point.setSRID(3857);
		location.setLegenda(legenda);
		location.setLocation(point);
		location.setVgiUser(new VgiUser(opIdUser.orElseThrow(
				()-> new UserNotInSessionException("L'utente non risulta essere in sessione"))));
		return saveOrUpdate(location);
	}
	
	public List<UserLocation> searchLocation(Optional<String> intervalloAnni, Optional<Long> idLegenda,
			Optional<Geometry> geom) {
			Integer annoA = null;
			Integer annoB = null;
			if (intervalloAnni.isPresent()) {
				String[] anni = intervalloAnni.get().split("-");
				annoA = new Integer(anni[0]);
				annoB = new Integer(anni[1]);
			}
			List<UserLocation> locations = userLocationDao.searchLocations(Optional.ofNullable(annoA),
					Optional.ofNullable(annoB), idLegenda, geom);
			return  locations;
	}
	
	public UserLocation update(Optional<UserLocation> ul) throws NullParamException {
		
		return saveOrUpdate(ul.orElseThrow(
				()-> (new NullParamException("Uno o più parametri non sono presenti nella request"))));
	}
	
	public Esito deleteLocation(Long idLocation) throws LinkedElementsExistException{
		delete(idLocation);
		return new Esito("Operazione eseguita senza errori", true);
	}
	
	public List<UserLocation> getUserLocations(Optional<Long> idUser) throws UserNotInSessionException {
		
		return findByIdUser(idUser.orElseThrow(()->new UserNotInSessionException("L'utente non risulta essere in sessione")));
	}
	
	public List<UserLocation> getUserLocationsByLegenda(Optional<Long> idUser, Long idLegenda) throws UserNotInSessionException {
		
		return findByUserAndLegenda(idUser.orElseThrow(()->new UserNotInSessionException("L'utente non risulta essere in sessione")), idLegenda);
	}
	
	
	public UserLocation getUserLocationById(Long idLocation) throws ElementNotFoundException {
		
		return findById(idLocation).orElseThrow(()->new ElementNotFoundException("L'elemento indicato non esiste"));
	}
	

}
