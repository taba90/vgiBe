package it.volpini.vgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;


@Entity
@Table(name="user_locations")
public class UserLocation extends AbstractEntity{
	
	private String indirizzo;
	
	@Column(columnDefinition = "TEXT")
	private String descrizione;
	
	@JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
	private Point location;
	
	private String codiceLegenda;
	
	@ManyToOne
	private VgiUser vgiUser;
	
	@ManyToOne
	private GhostUser ghostUser;
	

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	

	public VgiUser getVgiUser() {
		return vgiUser;
	}

	public void setVgiUser(VgiUser vgiUser) {
		this.vgiUser = vgiUser;
		vgiUser.getLocations().add(this);
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public GhostUser getGosthUser() {
		return ghostUser;
	}

	public void setGosthUser(GhostUser ghostUser) {
		this.ghostUser = ghostUser;
		ghostUser.getLocations().add(this);
	}

	public String getCodiceLegenda() {
		return codiceLegenda;
	}

	public void setCodiceLegenda(String codiceLegenda) {
		this.codiceLegenda = codiceLegenda;
	}
	
	

}
