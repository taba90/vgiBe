package it.volpini.vgi.restcontroller;

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
import it.volpini.vgi.general.Result;
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
	public Result<UserLocation> newLocation(@RequestBody UserLocation location, @PathVariable Long idLegenda){
		Optional<UserLocation> opLoc=Optional.ofNullable(location);
		Optional<Long> opIdAuth=userService.getIdAuthenticatedUser();
		return locationService.saveLocation(opLoc, opIdAuth, idLegenda);
	}
	
	@GetMapping("/search")
	public Result<UserLocation> search(@RequestParam(value="intervalloAnni", required=false) String intervalloAnni, 
			@RequestParam(value="idLegenda", required=false) Long idLegenda, @RequestParam(value="geom", required=false) Geometry geom){
		Optional<String> opInterval=Optional.ofNullable(intervalloAnni);
		Optional<Long>idLegOp=Optional.ofNullable(idLegenda);
		Optional<Geometry> opGeom=Optional.ofNullable(geom);
		return locationService.searchLocation(opInterval, idLegOp, opGeom);
	}
	
	@PatchMapping("/update")
	public Result<UserLocation> udpate(@RequestBody UserLocation location){
		Optional<UserLocation> opLoc=Optional.ofNullable(location);
		return locationService.update(opLoc);
	}
	
	@DeleteMapping("/{idLoc}/delete")
	public Result<UserLocation> delete(@PathVariable Long idLoc){
		Optional<Long> opId=Optional.ofNullable(idLoc);
		return locationService.deleteLocation(opId);
	}
}
