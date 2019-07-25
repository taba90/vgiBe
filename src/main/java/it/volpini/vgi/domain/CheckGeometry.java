package it.volpini.vgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.locationtech.jts.geom.MultiPolygon;

@Entity
@Table(name="check_geometry")
public class CheckGeometry extends AbstractEntity{
	
	
	@Column(name="geom")
	private MultiPolygon geometry;
	
	public CheckGeometry () {
		
	}

	public MultiPolygon getGeometry() {
		return geometry;
	}

	public void setGeometry(MultiPolygon geometry) {
		this.geometry = geometry;
	}
	

}
