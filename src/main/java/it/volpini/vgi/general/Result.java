package it.volpini.vgi.general;

import java.util.List;
import java.util.Optional;

public class Result<T> {
	
	private T result;
	
	private List<T> results;
	
	private Esito esito;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}
	
}
