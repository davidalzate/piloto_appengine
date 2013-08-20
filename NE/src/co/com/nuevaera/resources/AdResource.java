package co.com.nuevaera.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.UsuarioDto;
import co.com.nuevaera.client.dto.ViewDto;
import co.com.nuevaera.context.AppContextAware;
import co.com.nuevaera.model.NuevaEraPersistCtrl;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

public class AdResource extends ServerResource {

	

	@Get("json")
	public String handleGet() {
		String rep = null;
		try {
			
			NuevaEraPersistCtrl persistCtrl = new NuevaEraPersistCtrl();
			ArrayList<UsuarioDto> userList = persistCtrl.getUsuarios();
			
			//String idRestaurante = getQuery().getValues("idRestaurante");
			
			UserService userService = UserServiceFactory.getUserService();
			String user = userService.getCurrentUser().getEmail();
			Long idRestaurante = persistCtrl.getUsuario(user).getIdRestaurante();

			if (null != idRestaurante) {

				String propiedad = getQuery().getValues("propiedad");
				
				AppContextAware appContextAware = new AppContextAware();
				appContextAware.setIdRestaurante(idRestaurante);
				appContextAware.setPropiedad(propiedad);
				
				ArrayList<AnuncioDto> elementos = persistCtrl.getAnuncios(appContextAware);
				Gson gson = new Gson();
				rep  = gson.toJson(elementos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rep;
	}	
	

}
