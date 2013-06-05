package co.com.nuevaera.resources;

import java.util.ArrayList;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.model.NuevaEraPersistCtrl;

import com.google.gson.Gson;

public class CategoryResource extends ServerResource {

	@Get("json")
	public String handleGet() {
		String rep = null;
		try {
			String idRestaurante = getQuery().getValues("idRestaurante");

			if (null != idRestaurante) {


				NuevaEraPersistCtrl persistCtrl = new NuevaEraPersistCtrl();
				ArrayList<CategoriaDto> elementos = persistCtrl.getCategorias(new Long(idRestaurante), false);
				
				Gson gson = new Gson();
				rep  = gson.toJson(elementos);
//				JSONObject json = new JSONObject(elementos);
//				System.out.println("xx"+elementos.size()+"-xxxxxx="+json.toString());
//				
//				JSONArray jsonArray = new JSONArray(Arrays.asList(elementos));
//				JsonRepresentation jsonRep = new JsonRepresentation(jsonArray);
//				System.out.println(jsonArray.toString());
//				rep =  jsonRep.getText();
//				
				

//				for (Iterator iterator = elementos.iterator(); iterator
//						.hasNext();) {
//					ElementoDto elementoDto = (ElementoDto) iterator.next();
//					json.put("nombre", elementoDto.getNombre());
//					json.put("nombre", elementoDto.getNombre());
//					json.put("nombre", elementoDto.getNombre());
//					json.put("nombre", elementoDto.getNombre());
//					json.put("nombre", elementoDto.getNombre());
//					json.put("nombre", elementoDto.getNombre());
//					
//				}
				System.out.println("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rep;
	}

}
