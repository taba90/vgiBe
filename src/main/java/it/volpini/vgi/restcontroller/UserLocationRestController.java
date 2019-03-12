package it.volpini.vgi.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vividsolutions.jts.geom.Geometry;

import it.volpini.vgi.domain.UserLocation;
import it.volpini.vgi.exceptions.ElementNotFoundException;
import it.volpini.vgi.exceptions.LinkedElementsExistException;
import it.volpini.vgi.general.Esito;
import it.volpini.vgi.service.UserLocationService;
import it.volpini.vgi.service.VgiUserService;

@RestController
@RequestMapping("/location")
public class UserLocationRestController {
	
	@Autowired
	private UserLocationService locationService;
	
	@Autowired
	private VgiUserService userService;
	
	@PostMapping("{idLegenda}/new")
	public UserLocation newLocation(@RequestBody UserLocation location, @PathVariable Long idLegenda){
		Long idAuth=userService.getIdAuthenticatedUser();
		return locationService.saveLocation(location, idAuth, idLegenda);
	}
	
	@GetMapping("/search")
	public List<UserLocation> search(@RequestParam(value="intervalloAnni", required=false) String intervalloAnni, 
			@RequestParam(value="idLegenda", required=false) Long idLegenda, @RequestParam(value="geom", required=false) Geometry geom){
		Optional<String> opInterval=Optional.ofNullable(intervalloAnni);
		Optional<Long>idLegOp=Optional.ofNullable(idLegenda);
		Optional<Geometry> opGeom=Optional.ofNullable(geom);
		return locationService.searchLocation(opInterval, idLegOp, opGeom);
	}
	
	@PatchMapping()
	public UserLocation udpate(@RequestBody UserLocation location) {
		return locationService.update(location);
	}
	
	@DeleteMapping("/{idLoc}")
	public Esito delete(@PathVariable Long idLoc) throws LinkedElementsExistException{
		return locationService.deleteLocation(idLoc);
	}
	
	@GetMapping("/user")
	public List<UserLocation> getUserLocations() {
		return locationService.getUserLocations(userService.getIdAuthenticatedUser());
	}
	
	@GetMapping("/{idLegenda}/user")
	public List<UserLocation> getUserLocationsByIdLegenda(@PathVariable Long idLegenda) {
		Long idAuth=userService.getIdAuthenticatedUser();
		return locationService.getUserLocationsByLegenda(idAuth, idLegenda);
	}
	
	@GetMapping("/{idLocation}")
	public UserLocation getUserLocationById(@PathVariable Long idLocation) throws ElementNotFoundException{
		return locationService.getUserLocationById(idLocation);
	}
}
