package it.volpini.vgi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;



@Component
public class MailService {
	
	@Autowired
    private JavaMailSender emailSender;
    @Autowired
    private Environment env;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    
    public String getTextResetPwdMail (String token, String username) {
    	StringBuffer textBuf = new StringBuffer("Servizio di reset della password ");
    	textBuf.append("\n\r");
    	textBuf.append("E' stata mandata una richiesta per il rinnovo della password di questo account.");
    	textBuf.append("\n\r");
    	textBuf.append("Il tuo nome utente è: ").append(username);
    	textBuf.append("\n\r");
    	textBuf.append("Se la richiesta è partita da te clicca nel link di seguito, altrimenti ignora la mail.");
        textBuf.append("\n\r");
        textBuf.append(env.getProperty("vgi.frontend.domain"))
        .append("/login?t=").append(token);
        textBuf.append("\n\r");
        textBuf.append("Il link rimarrà valido per un'ora.");
        textBuf.append("\n\r");
        textBuf.append("Saluti, Erdkunder Staff");
        return textBuf.toString();
    }

}
