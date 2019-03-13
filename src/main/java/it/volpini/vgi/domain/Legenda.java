package it.volpini.vgi.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="legenda")
public class Legenda extends AbstractEntity{
	
	@Column(unique=true)
	@NotNull
	private String codice;
	
	private String descrizione;
	
	private boolean active;
	
	@NotNull
	private String colore;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="legenda")
	private List<UserLocation> userLocations;
	
	public Legenda(Long id) {
		this.id=id;
	}
	
	public Legenda() {
		
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<UserLocation> getUserLocations() {
		return userLocations;
	}

	public void setUserLocations(List<UserLocation> userLocations) {
		this.userLocations = userLocations;
	}
	
	public void addUserLocation(UserLocation location) {
		if(this.userLocations == null) {
			this.userLocations=new ArrayList<UserLocation>();
		}
		this.userLocations.add(location);
	}
	
	
}
