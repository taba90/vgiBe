package it.volpini.vgi.service;

import java.util.List;
import java.util.Optional;

import org.geotools.coverage.grid.GridCoverage2D;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.volpini.vgi.dao.UserLocationDao;
import it.volpini.vgi.domain.UserLocation;
import it.volpini.vgi.manager.RasterizerManager;

@Service
public class GeodataProcessService {

	@Autowired
	private RasterizerManager rasterMan;	
	
	@Autowired
	private UserLocationDao locDao;
	
	public byte [] generateHeathMap (Optional<Integer> annoA, Optional<Integer> annoB, Optional<Long> idLegenda,
			Optional<Geometry> geom,  Optional<Integer> width,  Optional<Integer> height,  Optional<Integer> kernelRadius,  Optional<Integer> pixelPerCell) throws Exception {
		
		List<UserLocation> locations = locDao.searchLocations(annoA,
				annoB, idLegenda, geom);
		GridCoverage2D heathmap = rasterMan.generateHeathMap(locations, width.orElseGet(()-> 100), height.orElseGet(()->100), 
				kernelRadius.orElseGet(()->20), pixelPerCell.orElseGet(()->1));
		return rasterMan.rasterToBytes(heathmap);
	}
}
