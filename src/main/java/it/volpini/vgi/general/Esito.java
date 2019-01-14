package it.volpini.vgi.general;

public class Esito {
	
	private String codice;
	
	private String descrizione;
	
	public Esito(String codice, String descrizione) {
		this.codice=codice;
		this.descrizione=descrizione;
	}
	
	public Esito () {
		
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
	
	

}
