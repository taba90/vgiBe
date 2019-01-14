package it.volpini.vgi.domain;

import javax.persistence.Entity;

@Entity
public class IntervalloEta extends AbstractEntity{
	
	private Integer limiteInferiore;
	
	private Integer limiteSuperiore;
	
	private String intervallo;

	public Integer getLimiteInferiore() {
		return limiteInferiore;
	}

	public void setLimiteInferiore(Integer limiteInferiore) {
		this.limiteInferiore = limiteInferiore;
	}

	public Integer getLimiteSuperiore() {
		return limiteSuperiore;
	}

	public void setLimiteSuperiore(Integer limiteSuperiore) {
		this.limiteSuperiore = limiteSuperiore;
	}

	public String getIntervallo() {
		return intervallo;
	}

	public void setIntervallo(String intervallo) {
		this.intervallo = intervallo;
	}
	

}
