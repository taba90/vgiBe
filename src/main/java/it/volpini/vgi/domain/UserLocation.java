package it.volpini.vgi.domain;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	private Float longitude;
	
	private Float latitude;
	
	@JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
	private Point location;
	
    @ManyToOne(fetch=FetchType.EAGER)
	private Legenda legenda;
    
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
		if(vgiUser.getLocations()==null) {
			vgiUser.setLocations(new ArrayList<UserLocation>());
		}
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

	public Legenda getLegenda() {
		return legenda;
	}

	public void setLegenda(Legenda legenda) {
		this.legenda = legenda;
		if(legenda.getUserLocations()==null) {
			legenda.setUserLocations(new ArrayList<UserLocation>());
		}
		legenda.getUserLocations().add(this);
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	
	

}
