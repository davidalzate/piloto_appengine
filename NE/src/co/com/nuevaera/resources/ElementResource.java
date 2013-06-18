package co.com.nuevaera.resources;

import java.util.ArrayList;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.model.NuevaEraPersistCtrl;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

public class ElementResource extends ServerResource {

	@Get("json")
	public String handleGet() {
		String rep = null;
		try {
			String idCategoria = getQuery().getValues("idCategoria");
			NuevaEraPersistCtrl persistCtrl = new NuevaEraPersistCtrl();
			if (null != idCategoria) {
				
				ArrayList<ElementoDto> elementos = persistCtrl.getElementos(new Long(idCategoria), false);
				Gson  gson = new Gson();
				rep = gson.toJson(elementos);
			}else {
				UserService userService = UserServiceFactory.getUserService();
				String user = userService.getCurrentUser().getEmail();
				Long idRestaurante = persistCtrl.getUsuario(user).getIdRestaurante();
				
				ArrayList<CategoriaDto> categorias = persistCtrl.getCategorias(new Long(idRestaurante), false);
				ArrayList<ElementoDto> elementos  = new ArrayList<ElementoDto>();
				for (CategoriaDto categoriaDto : categorias) {
					ArrayList<ElementoDto> tmpElementos = persistCtrl.getElementos(categoriaDto.getIdCategoria(), false);
					elementos.addAll(tmpElementos);
				}
				Gson  gson = new Gson();
				rep = gson.toJson(elementos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rep;
	}

}
