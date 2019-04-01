package it.volpini.vgi.general;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
public class CostantiVgi {
	
	public static final String CODICE_OK="000";
	public static final String CODICE_OK_RESULT_NULL="001";
	public static final String CODICE_ERRORE="002";
	public static final String DESCR_OK="Operazione eseguita con successo";
	public static final String DESCR_OK_RESULT_NULL="Nessun risultato";
	public static final String DESCR_ERRORE="Operazione terminata con errori: ";
	public static final String CLAIM_SUBJECT = "subject";
	public static final String LONGIN_ISSUE="login";
	public static final String RESET_ISSUE="reset";
	public static final String AUTH_TOKEN_KEY = "X-Vgi";
	
	@Value("${vgi.secret}")
	public static String secret;
	
	@Value("${vgi.max.point.per.legenda}")
	public static Integer max_point_legenda;
	

}
