package co.com.nuevaera.resources;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.dto.UsuarioDto;
import co.com.nuevaera.model.NuevaEraPersistCtrl;

public class UserResource extends ServerResource {
	@Get("json")
	public String handleGet() {
		UserService userService = UserServiceFactory.getUserService();
		String user = userService.getCurrentUser().getEmail();
		String rep = null;
		try {
			if (null != user) {
				NuevaEraPersistCtrl persistCtrl = new NuevaEraPersistCtrl();
				String all = getQuery().getValues("all");
				if (null != all&&all.trim().equalsIgnoreCase("true")) {
					ArrayList<UsuarioDto> userList = persistCtrl.getUsuarios();
					Gson  gson = new Gson();
					rep = gson.toJson(userList);					
				}else{
					UsuarioDto  usuarioDto = persistCtrl.getUsuario(user);
					ArrayList<UsuarioDto> userList = new ArrayList<UsuarioDto>();
					userList.add(usuarioDto);
					Gson  gson = new Gson();
					rep = gson.toJson(userList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rep;
	}
	




}
