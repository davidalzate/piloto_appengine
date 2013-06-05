package co.com.nuevaera.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.ViewDto;
import co.com.nuevaera.context.AppContextAware;
import co.com.nuevaera.model.NuevaEraPersistCtrl;

import com.google.gson.Gson;

public class AdResource extends ServerResource {

	

	@Get("json")
	public String handleGet() {
		String rep = null;
		try {
			String idRestaurante = getQuery().getValues("idRestaurante");

			if (null != idRestaurante) {

				String propiedad = getQuery().getValues("propiedad");
				
				AppContextAware appContextAware = new AppContextAware();
				appContextAware.setIdRestaurante(new Long(idRestaurante));
				appContextAware.setPropiedad(propiedad);
				
				NuevaEraPersistCtrl persistCtrl = new NuevaEraPersistCtrl();
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
