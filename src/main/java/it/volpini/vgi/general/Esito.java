package it.volpini.vgi.general;

public class Esito {
		
	private String descrizione;
	
	private boolean esito;
	
	public Esito(String descrizione, boolean esito) {
		this.descrizione=descrizione;
		this.esito = esito;
	}
	
	public Esito () {
		
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}
}
