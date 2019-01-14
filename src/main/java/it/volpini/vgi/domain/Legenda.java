package it.volpini.vgi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="legenda")
public class Legenda extends AbstractEntity{
	
	@Column(unique=true)
	private String codice;
	
	private String descrizione;
	
	private boolean active;
	
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
