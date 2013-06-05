package co.com.nuevaera.client;

import java.util.List;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.AnuncioEmisionDto;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.dto.RestauranteDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.SuggestOracle;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("neService")
public interface NEService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;

	List<RestauranteDto> getRestaurantes();
	
	boolean save(RestauranteDto restauranteDto);
	
	boolean delete(RestauranteDto restauranteDto);
	
	boolean update(RestauranteDto restauranteDto);

	List<EmpresaDto> getEmpresas();
	
	boolean deleteEmpresa(EmpresaDto empresaDto);
	
	boolean saveEmpresa(EmpresaDto empresaDto);
	
	List<EmisionDto> getEmisiones();
	
	boolean deleteEmision(EmisionDto emisionDto);
	
	boolean saveEmision(EmisionDto emisionDto);
	
	List<CategoriaDto> getCategorias(long idRestaurante);
	
	boolean deleteCategoria(CategoriaDto categoriaDto);
	
	boolean saveCategoria(CategoriaDto categoriaDto);
	
	public SuggestOracle.Response getSuggestions(SuggestOracle.Request req);
	
	public static class Util {
		public static NEServiceAsync getInstance() {
			NEServiceAsync suggestService =  GWT
					.create(NEService.class);

			return suggestService;
		}
	}
	
	List<ElementoDto> getElementos(long idElemento);
	
	boolean deleteElemento(ElementoDto elementoDto);
	
	boolean saveElemento(ElementoDto elementoDto);
	
	
	List<AnuncioDto> getAnuncios(long idElemento);
	
	public List<AnuncioDto> getAllAnuncios();
	
	boolean deleteAnuncio(AnuncioDto anuncioDto);
	
	boolean saveAnuncio(AnuncioDto anuncioDto);
	
	public String getBlobstoreUploadUrl();
	
	public String getImageUrl(String key);
	
	List<AnuncioDto> getAnunciosEmision(EmisionDto emision);
	
	public boolean addAnuncioEmision(AnuncioEmisionDto anuncioEmisionDto);
}
