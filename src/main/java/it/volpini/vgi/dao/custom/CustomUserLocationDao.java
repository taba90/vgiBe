package it.volpini.vgi.dao.custom;


import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Geometry;

import it.volpini.vgi.domain.UserLocation;


public interface CustomUserLocationDao{
	
	public List<UserLocation> searchLocations(Optional<Integer> anni1, Optional<Integer> anni2, Optional<Long> idLegenda, Optional<Geometry> geom);
	
	

}
