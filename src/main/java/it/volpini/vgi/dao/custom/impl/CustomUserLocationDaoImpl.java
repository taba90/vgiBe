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
		StringBuffer strquery=new StringBuffer("select ul from UserLocation ul ");
		boolean and=false;
		if(anni1.isPresent() || anni2.isPresent()) {
			strquery.append(" where (" );
			strquery.append(" ul.vgiUser.id in (");
			strquery.append(" select u.id from VgiUser u where ");
			if(anni1.isPresent()) {
				strquery.append(" u.anni >= :anni1 ");
				and=true;
			}
			if(anni2.isPresent()) {
				if(and) {
					strquery.append(" and ");
				} else {
					strquery.append(" where " );
				}
				strquery.append(" u.anni <= :anni2 ");
				and=true;
			}
			strquery.append(") or ul.ghostUser.id in (");
			strquery.append(" select gu.id from GhostUser gu where ");
			if(anni1.isPresent()) {
				strquery.append(" gu.anni >= :anni1 ");
				and=true;
			} else {
				and = false;
			}
			if(anni2.isPresent()) {
				if(and) {
					strquery.append(" and ");
				} else {
					strquery.append(" where " );
				}
				strquery.append(" gu.anni <= :anni2 ");
				and=true;
			}
			strquery.append(")");
			strquery.append(")");
		}
		if(idLegenda.isPresent()) {
			if(and) {
				strquery.append(" and ");
			} else {
				strquery.append(" where " );
			}
			strquery.append(" ul.legenda.id = :idLegenda ");
			and=true;
		}
		if(geom.isPresent()) {
			if(and) {
				strquery.append(" and ");
			} else {
				strquery.append(" where " );
			}
			strquery.append(" within(ul.location, :geom)=true ");
			and=true;
		}
		Query query = eM.createQuery(strquery.toString());
		anni1.ifPresent((anniDa)-> { 
			query.setParameter("anni1", anniDa);
			});
		anni2.ifPresent((anniA)-> {
			query.setParameter("anni2", anniA);			
			});
		idLegenda.ifPresent((idLeg)-> query.setParameter("idLegenda", idLeg));
		return query.getResultList();
	}
	
	
	

}
