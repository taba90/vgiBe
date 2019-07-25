package it.volpini.vgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Point;


@Entity
@Table(name="user_locations")
public class UserLocation extends AbstractEntity{
	
	@Column(columnDefinition = "TEXT")
	private String descrizione;
	
	private String nome;
	
	@NotNull
	private Float longitude;
	
	@NotNull
	private Float latitude;
	
	@JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
	private Point location;
	
	@NotNull
    @ManyToOne(fetch=FetchType.EAGER)
	private Legenda legenda;
    
	@ManyToOne
	private VgiUser vgiUser;
	
	@ManyToOne
	private GhostUser ghostUser;
	
	

	public VgiUser getVgiUser() {
		return vgiUser;
	}

	public void setVgiUser(VgiUser vgiUser) {
		this.vgiUser = vgiUser;
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
	}

	public Legenda getLegenda() {
		return legenda;
	}

	public void setLegenda(Legenda legenda) {
		this.legenda = legenda;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GhostUser getGhostUser() {
		return ghostUser;
	}

	public void setGhostUser(GhostUser ghostUser) {
		this.ghostUser = ghostUser;
	}
	
	
	
	

}
