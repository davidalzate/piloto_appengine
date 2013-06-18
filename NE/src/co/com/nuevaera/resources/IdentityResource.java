package co.com.nuevaera.resources;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.AnuncioEmisionDto;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.dto.UsuarioDto;
import co.com.nuevaera.model.NuevaEraPersistCtrl;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

public class IdentityResource extends ServerResource {

	@Get("json")
	public String handleGet() {
		String resp = new String();
		NuevaEraPersistCtrl persistCtrl  = new NuevaEraPersistCtrl();
		//String idParam = getQuery().getValues("id");
		//Long idRestaurante = new Long(idParam);
		
		UserService userService = UserServiceFactory.getUserService();
		String user = userService.getCurrentUser().getEmail();
		Long idRestaurante = persistCtrl.getUsuario(user).getIdRestaurante();
		
		/*try {			
			if(null!=getQuery().getValues("populate")){
				if(getQuery().getValues("populate").equalsIgnoreCase("yes")){
					populateTest();					
				}
			}
			JSONObject json = new JSONObject();
			JsonRepresentation jsonRep = new JsonRepresentation(json);
			
			String idParam = getQuery().getValues("id");
			NuevaEraPersistCtrl persistCtrl  = new NuevaEraPersistCtrl();
			
			if(null!=idParam){
				json.put("name", "value");
				
				
				Long idRestaurante = new Long(idParam); */
				
				RestauranteDto restauranteDto = persistCtrl.getRestaurante(idRestaurante, false);
				ArrayList<RestauranteDto> restauranteDtos = new ArrayList<RestauranteDto>();
				restauranteDtos.add(restauranteDto);
				Gson  gson = new Gson();
				resp = gson.toJson(restauranteDtos);
				/*
				if(null!=restauranteDto){
					json.put("name", restauranteDto.getNombre());
					json.put("banner", restauranteDto.getBanner());
					json.put("fondo", restauranteDto.getFondo());					
				}
				
				resp =  jsonRep.getText();
			}else{
				ArrayList<RestauranteDto> restaurantes = persistCtrl.getRestaurantes();
				Gson gson = new Gson();
				resp = gson.toJson(restaurantes);
			}			
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return resp;
	}
	
	private void populateTest(){

		NuevaEraPersistCtrl persistCtrl  = new NuevaEraPersistCtrl();
		
		RestauranteDto restauranteDto = new RestauranteDto();
		restauranteDto.setNombre("R2");
		restauranteDto.setBanner("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		restauranteDto.setFondo("http://www.hotelalmirantecartagena.com.co/images/banner-contacto.jpg");

		Long idRestaurante = persistCtrl.addRestaurante(restauranteDto);
		
		System.out.println("idR="+idRestaurante);
		
		CategoriaDto categoriaDto = new CategoriaDto();
		categoriaDto.setNombre("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		categoriaDto.setFoto("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		categoriaDto.setIdRestaurante(idRestaurante);
		
		long c1 = persistCtrl.addCategoria(categoriaDto);
		
		System.out.println("idc="+c1);
				
		ElementoDto elementoDto = new ElementoDto();
		elementoDto.setIdCategoria(c1);
		elementoDto.setNombre("E1");
		elementoDto.setDescripcionCorta("E1Dc");
		elementoDto.setDescripcionLarga("E1Dl");
		elementoDto.setDisponible(true);
		elementoDto.setFotoBig("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		elementoDto.setFotoSmall("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		elementoDto.setPrecio("Ep1");

		persistCtrl.addElemento(elementoDto);
		
		elementoDto.setIdCategoria(c1);
		elementoDto.setNombre("E2");
		elementoDto.setDescripcionCorta("E2Dc");
		elementoDto.setDescripcionLarga("E2Dl");
		elementoDto.setDisponible(true);
		elementoDto.setFotoBig("http://princetonusg.com/wp-content/uploads/2012/12/combo.png");
		elementoDto.setFotoSmall("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		elementoDto.setPrecio("Ep2");		

		persistCtrl.addElemento(elementoDto);
		

		categoriaDto.setNombre("C2");
		categoriaDto.setFoto("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		categoriaDto.setIdRestaurante(idRestaurante);		
		
		long c2 = persistCtrl.addCategoria(categoriaDto);

		elementoDto.setIdCategoria(c2);
		elementoDto.setNombre("E3");
		elementoDto.setDescripcionCorta("E3Dc");
		elementoDto.setDescripcionLarga("E3Dl");
		elementoDto.setDisponible(true);
		elementoDto.setFotoBig("http://3.bp.blogspot.com/_ocu0FRvy8aA/TJBHikZ-LOI/AAAAAAAAAAs/m9sCYKiIVOU/s1600/BANNER+COMIDA4.jpg");
		elementoDto.setFotoSmall("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		elementoDto.setPrecio("Ep3");

		persistCtrl.addElemento(elementoDto);
		
		elementoDto.setIdCategoria(c2);
		elementoDto.setNombre("E4");
		elementoDto.setDescripcionCorta("E4Dc");
		elementoDto.setDescripcionLarga("E4Dl");
		elementoDto.setDisponible(true);
		elementoDto.setFotoBig("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		elementoDto.setFotoSmall("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		elementoDto.setPrecio("Ep4");		

		persistCtrl.addElemento(elementoDto);

		categoriaDto.setNombre("C3");
		categoriaDto.setFoto("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		categoriaDto.setIdRestaurante(idRestaurante);		
		
		long c3 = persistCtrl.addCategoria(categoriaDto);

		elementoDto.setIdCategoria(c3);
		elementoDto.setNombre("E5");
		elementoDto.setDescripcionCorta("E5Dc");
		elementoDto.setDescripcionLarga("E5Dl");
		elementoDto.setDisponible(true);
		elementoDto.setFotoBig("http://1.bp.blogspot.com/_ocu0FRvy8aA/TJBHyNKy4RI/AAAAAAAAAA0/BmKNOuLO-cI/s1600/BANNER+COMIDA5.jpg");
		elementoDto.setFotoSmall("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		elementoDto.setPrecio("Ep5");

		persistCtrl.addElemento(elementoDto);
		
		elementoDto.setIdCategoria(c3);
		elementoDto.setNombre("E6");
		elementoDto.setDescripcionCorta("E6Dc");
		elementoDto.setDescripcionLarga("E6Dl");
		elementoDto.setDisponible(true);
		elementoDto.setFotoBig("http://www.freewebs.com/cabanas-combate/BANNER%20COMIDA.jpg");
		elementoDto.setFotoSmall("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		elementoDto.setPrecio("Ep6");		

		persistCtrl.addElemento(elementoDto);
		
		
		EmpresaDto empresa = new EmpresaDto();
		empresa.setNombre("Nueva Era");

		long e1 = persistCtrl.addEmpresa(empresa);
		
		AnuncioDto anuncioDto = new AnuncioDto();
		anuncioDto.setIdEmpresa(e1);
		anuncioDto.setFotoSmall("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		anuncioDto.setFotoBig("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		anuncioDto.setDuracion(400);
		
		long ida1 = persistCtrl.addAnuncio(anuncioDto);

		anuncioDto.setIdEmpresa(e1);
		anuncioDto.setFotoSmall("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		anuncioDto.setFotoBig("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		anuncioDto.setDuracion(400);
		
		long ida2 = persistCtrl.addAnuncio(anuncioDto);

		
		anuncioDto.setIdEmpresa(e1);
		anuncioDto.setFotoSmall("http://1.bp.blogspot.com/_ocu0FRvy8aA/TJBHyNKy4RI/AAAAAAAAAA0/BmKNOuLO-cI/s1600/BANNER+COMIDA5.jpg");
		anuncioDto.setFotoBig("http://www.ofifacil.com/docs/trabajos-diseno-grafico-graphic-design/diseno-banners-design/banner-restaurante04.jpg");
		anuncioDto.setDuracion(400);
		
		long ida3 = persistCtrl.addAnuncio(anuncioDto);
		
		
		EmisionDto emisionDto = new EmisionDto();
		emisionDto.setIdRestaurante(idRestaurante);
		emisionDto.setPropiedad("semanasanta");
		
		long idae1 = persistCtrl.addEmision(emisionDto);
		
		AnuncioEmisionDto anuncioEmisionDto = new AnuncioEmisionDto();
		anuncioEmisionDto.setIdEmision(idae1);
		anuncioEmisionDto.setIdAnuncio(ida1);
		
		persistCtrl.addAnuncioEmision(anuncioEmisionDto);
		

		anuncioEmisionDto.setIdEmision(idae1);
		anuncioEmisionDto.setIdAnuncio(ida2);
		
		persistCtrl.addAnuncioEmision(anuncioEmisionDto);

		
		anuncioEmisionDto.setIdEmision(idae1);
		anuncioEmisionDto.setIdAnuncio(ida3);
		
		persistCtrl.addAnuncioEmision(anuncioEmisionDto);
		
		
		UserService userService = UserServiceFactory.getUserService();
		String user = userService.getCurrentUser().getEmail();
		
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setIdRestaurante(idRestaurante);
		usuarioDto.setUsuario(user);
		
		persistCtrl.addUser(usuarioDto);

	}
}