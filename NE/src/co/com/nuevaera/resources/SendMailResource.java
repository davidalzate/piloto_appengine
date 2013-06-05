package co.com.nuevaera.resources;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.model.NuevaEraPersistCtrl;

import com.google.gson.Gson;

public class SendMailResource extends ServerResource {
	@Get("json")
	public String handleGet() {
		String rep = null;
		try {
			String idRestaurante = getQuery().getValues("idRestaurante");
			String to = getQuery().getValues("to");
			if (null != idRestaurante) {
				
				String idAnuncio = getQuery().getValues("idAnuncio");

				
				NuevaEraPersistCtrl persistCtrl = new NuevaEraPersistCtrl();
				AnuncioDto anuncio = persistCtrl.getAnuncio(Long.valueOf(idAnuncio));
				RestauranteDto restaurante  = persistCtrl.getRestaurante(Long.valueOf(idRestaurante), false);
				String mensaje = "<img src='http://www.clubcomida.com/images/uploads/banners_club-comida1.png'/>";
				sendMail(to, mensaje);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rep;
	}
	
	private void sendMail(String to, String msgBody) throws UnsupportedEncodingException{
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

       

        try {
            Message msg = new MimeMessage(session);	
            msg.setFrom(new InternetAddress("juandelmartillo@gmail.com", "nuevaera.com Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(to, "Usuario Nueva Era"));
            msg.setSubject("Nueva Era");

            Multipart mp = new MimeMultipart();

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(msgBody, "text/html");
            mp.addBodyPart(htmlPart);
            msg.setContent(mp);
            //msg.setText(msgBody);
            Transport.send(msg);
    
        } catch (AddressException e) {
        	e.printStackTrace();
        } catch (MessagingException e) {
        	e.printStackTrace();
        }		
	}



}
