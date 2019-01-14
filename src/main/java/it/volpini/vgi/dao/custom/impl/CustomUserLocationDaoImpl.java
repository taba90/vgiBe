package it.volpini.vgi.dao.custom.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Geometry;

import it.volpini.vgi.dao.custom.CustomUserLocationDao;
import it.volpini.vgi.domain.UserLocation;

@Repository
public class CustomUserLocationDaoImpl implements CustomUserLocationDao{
	
	@PersistenceContext
	private EntityManager eM;

	@Override
	public List<UserLocation> searchLocations(Optional<Integer> anni1, Optional<Integer> anni2, Optional<Long> idLegenda, Optional<Geometry> geom) {
		StringBuffer strquery=new StringBuffer("select ul from UserLocation ul where ");
		boolean and=false;
		if(anni1.isPresent()) {
			strquery.append(" ul.vgiUser.anni >= :anni1 ");
			and=true;
		}
		if(anni2.isPresent()) {
			if(and) {
				strquery.append(" and ");
			}
			strquery.append(" ul.vgiUser.anni <= :anni2 ");
			and=true;
		}
		if(idLegenda.isPresent()) {
			if(and) {
				strquery.append(" and ");
			}
			strquery.append(" ul.vgiUser.legenda.id <= :idLegenda ");
			and=true;
		}
		if(geom.isPresent()) {
			if(and) {
				strquery.append(" and ");
			}
			strquery.append(" within(ul.location, :geom)=true ");
			and=true;
		}
		Query query=eM.createQuery(strquery.toString());
		return null;
	}
	
	
	

}
