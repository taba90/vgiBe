package it.volpini.vgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.MultiPolygon;

@Entity
@Table(name="check_geometry")
public class CheckGeometry extends AbstractEntity{
		
	@Column(name="codice_comune")
	private String codiceIstat;
	
	private String comune;
	
	@Column(name="geom")
	private MultiPolygon geometry;
	
	public CheckGeometry () {
		
	}

	public String getCodiceIstat() {
		return codiceIstat;
	}

	public void setCodiceIstat(String codiceIstat) {
		this.codiceIstat = codiceIstat;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public MultiPolygon getGeometry() {
		return geometry;
	}

	public void setGeometry(MultiPolygon geometry) {
		this.geometry = geometry;
	}
	

}
